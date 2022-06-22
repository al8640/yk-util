chmod +x ./gradlew
./gradlew clean assemble --refresh-dependencies -Denv=dev
docker build -f src/main/docker/Dockerfile -t registry.cn-hangzhou.aliyuncs.com/yangke/yk-util:dev .
docker push registry.cn-hangzhou.aliyuncs.com/yangke/yk-util:dev
