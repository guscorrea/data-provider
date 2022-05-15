package com.dt.dataprovider.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

	@Value("${mqtt.url}")
	private String url;

	@Value("${mqtt.username}")
	private String username;

	@Value("${mqtt.password}")
	private String password;

	@Value("${mqtt.clientId}")
	private String clientId;

	@Value("${mqtt.qos}")
	private int qos;

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { url });
		options.setUserName(username);
		options.setPassword(password.toCharArray());
		options.setCleanSession(true);
		options.setMaxInflight(1000);
		factory.setConnectionOptions(options);
		return factory;
	}

	@Bean
	public MessageChannel chokeValveOutboundChannel() {
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "chokeValveOutboundChannel")
	public MessageHandler mqttOutbound() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic("#");
		messageHandler.setConverter(new DefaultPahoMessageConverter());
		messageHandler.setDefaultRetained(false);
		messageHandler.setDefaultQos(qos);
		return messageHandler;
	}

}
