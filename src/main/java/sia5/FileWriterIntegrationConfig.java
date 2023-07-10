package sia5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.dsl.IntegrationDsl;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

//The  config below uses java configuration style (Optionally there is xml and DSL(Domain Specific Language).

/*@Configuration
public class FileWriterIntegrationConfig {

    @Bean
    @Transformer(inputChannel = "textInChannel",
                outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        return text -> text.toUpperCase();
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler =
                new FileWritingMessageHandler(new File("/tmp/sia5/files"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);

        return handler;
    }

    //Optional beans (used if you wish to have more control over how the channels are configured (otherwise they will be constructed automaticallY)
    @Bean
    public MessageChannel textInChannel() {
        return new DirectChannel();
    }
    @Bean
    public MessageChannel fileWriterChannel() {
        return new DirectChannel();
    }

}*/


//Below is a DSL Config whereby, Rather than declare
//an individual bean for each component in the flow, you’ll declare a single bean that
//defines the entire flow.

@Configuration
public class FileWriterIntegrationConfig {
    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlow //IntegrationFlows - remember to do research
                .from(MessageChannels.direct("textInChannel"))
                .<String, String>transform(t -> toString().toUpperCase())
                .channel(MessageChannels.direct("fileWriterChannel"))
                .handle(Files
                        .outboundAdapter(new File("tmp/sia/files")).
                        fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get();
    }
}
// Basics
// Channels—Pass messages from one element to another.
// Filters—Conditionally allow messages to pass through the flow based on some
//criteria.
// Transformers—Change message values and/or convert message payloads from
//one type to another.
// Routers—Direct messages to one of several channels, typically based on message headers.
// Splitters—Split incoming messages into two or more messages, each sent to different channels.
// Aggregators—The opposite of splitters, combining multiple messages coming in
//from separate channels into a single message.
// Service activators—Hand a message off to some Java method for processing, and
//then publish the return value on an output channel.
// Channel adapters—Connect a channel to some external system or transport. Can
//either accept input or write to the external system.
// Gateways—Pass data into an integration flow via an interface.