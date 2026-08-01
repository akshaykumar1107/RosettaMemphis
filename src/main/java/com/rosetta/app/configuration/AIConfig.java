package com.rosetta.app.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig
{
    //use @Qualifier("methodName") ChatClient chatClient -> to inject the required bean.
    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel)//OllamaChatModel bean is autowired.
    {
        return ChatClient.builder(ollamaChatModel).build();
    }

    @Bean
    public ChatClient geminiChatClient(GoogleGenAiChatModel googleGenAiChatModel)
    {
        return ChatClient.builder(googleGenAiChatModel).build();
    }
}
