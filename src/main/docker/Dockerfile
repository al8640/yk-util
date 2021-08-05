FROM registry.cn-shanghai.aliyuncs.com/ytx/jdk8_181:v2.0.1
ADD build/libs/rjhy-appraisal-business.jar /var/workspace
CMD java -javaagent:/opt/agent/skywalking-agent.jar \
         -Dskywalking.agent.service_name=rjhy-appraisal-business \
         -Dskywalking.collector.backend_service=10.0.13.170:11800 \
         -Dskywalking.agent.sample_n_per_3_secs=30 \
         -server \
         -Dfile.encoding=UTF-8 \
         -Xmx5120m \
         -Xms5120m \
         -Xmn3200M \
         -XX:+UseParallelGC \
         -XX:+UseAdaptiveSizePolicy \
         -XX:ParallelGCThreads=16 \
         -XX:MaxGCPauseMillis=100 \
         -verbose:gc \
         -XX:+PrintGCDetails \
         -XX:+PrintGCDateStamps \
         -XX:+PrintGCTimeStamps \
         -Xloggc:/var/workspace/logs/gc.log \
         -XX:+HeapDumpOnOutOfMemoryError \
         -XX:HeapDumpPath=/var/workspace/logs \
         -jar rjhy-appraisal-business.jar