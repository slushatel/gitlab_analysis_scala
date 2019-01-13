/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.livy.LivyClient;
import org.apache.livy.LivyClientBuilder;

import java.io.File;
import java.net.URI;


/**
 * Example execution:
 * java -cp /pathTo/spark-core_2.11-*version*.jar:/pathTo/livy-api-*version*.jar:
 * /pathTo/livy-client-http-*version*.jar:/pathTo/livy-examples-*version*.jar
 * org.apache.livy.examples.PiApp http://livy-host:8998 2 d:\work\github_poc\gitlab_analysis_scala\libs\gitlab-analysis-scala-1.0-SNAPSHOT.jar
 */
public class PiAppLauncher {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: PiAppJob <livy url> <slices> <path-to-job-jar>");
            System.exit(-1);
        }
        String livyUrl = args[0];
        int slices = Integer.parseInt(args[1]);
        String pathToJobJar = args[2];

        LivyClient client = new LivyClientBuilder().setURI(new URI(livyUrl)).build();

        try {
            client.uploadJar(new File(pathToJobJar)).get();
            double pi = client.submit(new PiAppJob(slices)).get();
            System.out.println("Pi is roughly " + pi);
        } finally {
            client.stop(true);
        }
    }
}

