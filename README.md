# netty_examples

1. compile source codes and pakcage the classes into a jar
    mvn clean;mvn assembly:assembly

2. specify main class in pom

	 <plugins>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <!--
                            <mainClass>com.xiami.chat.SecureChatClient</mainClass>
                             -->
                            <!--
                            <mainClass>com.xiami.codec.pojo.SubReqClient</mainClass>
                             -->
                            <mainClass>com.xiami.codec.protobuf.SubReqClient</mainClass>
                        </manifest>
                    </archive>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
            </plugin>
        </plugins>

3. run jar
   you should specify related main class in pom and then complie the sources codes, run server/client likes
   
   java -jar ./target/netty_examples-1.0-SNAPSHOT-jar-with-dependencies.jar
