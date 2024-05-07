package com.neo;

import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = HelloApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

@ContextConfiguration(initializers = JaegerIntegrationTest.Initializer.class)
public class JaegerIntegrationTest {

	private static final int QUERY_PORT = 16686;
	private static final int COLLECTOR_PORT = 14268;
	private static final String SERVICE_NAME = "spring-boot-hello";

	@ClassRule
	public static GenericContainer jaeger
			= new GenericContainer("jaegertracing/all-in-one:1.17")
			.withExposedPorts(COLLECTOR_PORT, QUERY_PORT)
			//make sure we wait until the collector is ready
			.waitingFor(new HttpWaitStrategy().forPath("/"));

	@Autowired
	private TestRestTemplate testRestTemplate;

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void testJaegerCollectsTraces() {
		final String operation = "hello";
		Assertions.assertThat(testRestTemplate.getForObject("/" + operation, String.class)).isNotBlank();

		waitJaegerQueryContains(SERVICE_NAME, operation);
	}

	private void waitJaegerQueryContains(String serviceName, String str) {
		Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> {
			try {
				final String output = restTemplate.getForObject(
						String.format(
								"%s/api/traces?service=%s",
								String.format(
										"http://localhost:%d",
										jaeger.getMappedPort(QUERY_PORT)
								),
								serviceName
						),
						String.class
				);
				return output.contains(str);
			} catch (Exception e) {
				return false;
			}
		});
	}

	//create the opentracing.jaeger properties from the information of the running container
	public static class Initializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					String.format(
							"opentracing.jaeger.http-sender.url=http://%s:%d/api/traces",
							jaeger.getContainerIpAddress(),
							jaeger.getMappedPort(COLLECTOR_PORT)
					),
					String.format("spring.application.name=%s", SERVICE_NAME)
			).applyTo(configurableApplicationContext);
		}
	}
}
