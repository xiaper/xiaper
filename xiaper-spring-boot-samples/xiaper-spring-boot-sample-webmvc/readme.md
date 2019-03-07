
# 说明

## 生成asciidoc

- 直接打包: mvn package


## 将jar发布到 DockerHub

- MAC安装docker
- brew cask install docker

- Spring官方教程： https://spring.io/guides/gs/spring-boot-docker/
- dockerfile仓库：https://github.com/spotify/dockerfile-maven

- [DockerHub](https://cloud.docker.com/)
- docker id: jackning
- email: 270580156@qq.com
- password: wUxSk7prRLQM%gW

- xiaper镜像地址
- https://hub.docker.com/r/xiaper/xiaper

- 打包：
- mvn install -Dmaven.test.skip=true dockerfile:build
- docker login
- docker push xiaper/xiaper

- 查看所有镜像
- docker images

- 删除多余镜像
- docker image rm IMAGEID

- 搜索镜像
- docker search xiaper/xiaper

- 安装镜像
- docker pull xiaper/xiaper

- 运行镜像
// java -jar -Dspring.config.location=/xiaper/application.properties springbootrestdemo.jar
//- docker run -d -p 8000:8000 xiaper/xiaper

