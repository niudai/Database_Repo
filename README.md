# 数据库大作业

## 项目简介

项目使用 _Spring Boot_ 作为后端开发框架，_Angular_ 作为前端开发框架，_Mysql_ 作为生产环境数据库，_Docker_ 作为生产环境部署方式，是一个全栈 Responsive 的 Web 应用，提供基本 USTC 信息库的增删查改操作。

## 项目文件结构

```
.
├── src
│   ├── main
│   │   ├── docker // docker 部署代码
│   │   │   ├── grafana
│   │   │   │   └── provisioning
│   │   │   │       ├── dashboards
│   │   │   │       └── datasources
│   │   │   └── prometheus
│   │   ├── java // java 后端代码
│   │   │   └── com
│   │   │       └── mycompany
│   │   │           └── myapp
│   │   │               ├── aop
│   │   │               │   └── logging
│   │   │               ├── config
│   │   │               │   └── audit
│   │   │               ├── domain // 实体 pojo 定义
│   │   │               │   └── enumeration
│   │   │               ├── repository // 数据库表定义
│   │   │               │   └── search
│   │   │               ├── security // 安全
│   │   │               │   └── jwt
│   │   │               ├── service // 服务 controller
│   │   │               │   ├── dto
│   │   │               │   └── mapper
│   │   │               └── web
│   │   │                   └── rest
│   │   │                       ├── errors
│   │   │                       └── vm
│   │   ├── jib
│   │   ├── resources // 配置数据相关静态资源
│   │   │   ├── config
│   │   │   │   ├── liquibase // 数据库重构版本控制
│   │   │   │   │   ├── changelog
│   │   │   │   │   ├── data
│   │   │   │   │   └── fake-data
│   │   │   │   └── tls // tls 配置
│   │   │   ├── i18n // 国际化模块
│   │   │   └── templates // 模板
│   │   │       └── mail // 邮件模板
│   │   └── webapp // 前端代码
│   │       ├── WEB-INF
│   │       ├── app
│   │       │   ├── account // 账号相关
│   │       │   │   ├── activate
│   │       │   │   ├── password
│   │       │   │   ├── password-reset
│   │       │   │   │   ├── finish
│   │       │   │   │   └── init
│   │       │   │   ├── register
│   │       │   │   └── settings
│   │       │   ├── admin // 管理页面
│   │       │   │   ├── audits
│   │       │   │   ├── configuration
│   │       │   │   ├── docs
│   │       │   │   ├── health
│   │       │   │   ├── logs
│   │       │   │   ├── metrics
│   │       │   │   └── user-management
│   │       │   ├── blocks
│   │       │   │   ├── config
│   │       │   │   └── interceptor
│   │       │   ├── core
│   │       │   │   ├── auth
│   │       │   │   ├── icons
│   │       │   │   ├── language
│   │       │   │   ├── login
│   │       │   │   └── user
│   │       │   ├── entities // 实体页面
│   │       │   │   ├── campus
│   │       │   │   ├── course
│   │       │   │   ├── grade
│   │       │   │   ├── interval
│   │       │   │   ├── j-exception
│   │       │   │   ├── major
│   │       │   │   ├── people
│   │       │   │   ├── record
│   │       │   │   ├── school-class
│   │       │   │   ├── semaster
│   │       │   │   ├── student
│   │       │   │   └── teacher
│   │       │   ├── home // 首页
│   │       │   ├── layouts // 模板
│   │       │   │   ├── error
│   │       │   │   ├── footer
│   │       │   │   ├── main
│   │       │   │   ├── navbar
│   │       │   │   └── profiles
│   │       │   └── shared // 共享库
│   │       │       ├── alert
│   │       │       ├── auth
│   │       │       ├── constants
│   │       │       ├── language
│   │       │       ├── login
│   │       │       ├── model
│   │       │       │   └── enumerations
│   │       │       └── util
│   │       ├── content
│   │       │   ├── css
│   │       │   ├── images
│   │       │   └── scss
│   │       ├── i18n
│   │       │   ├── en
│   │       │   └── fr
│   │       └── swagger-ui
│   │           └── dist
│   │               └── images
│  
└── webpack // webpack 相关配置
```

## 数据库表设计：

![Image](db-design.png)

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    npm install

We use npm scripts and [Webpack][] as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    npm start

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

Note: [Workbox](https://developers.google.com/web/tools/workbox/) powers JHipster's service worker. It dynamically generates the `service-worker.js` file.

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

    npm install --save --save-exact leaflet

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

    npm install --save-dev --save-exact @types/leaflet

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Edit [src/main/webapp/app/vendor.ts](src/main/webapp/app/vendor.ts) file:

```
import 'leaflet/dist/leaflet.js';
```

Edit [src/main/webapp/content/scss/vendor.scss](src/main/webapp/content/scss/vendor.scss) file:

```
@import '~leaflet/dist/leaflet.css';
```

Note: There are still a few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Using Angular CLI

You can also use [Angular CLI][] to generate some custom client code.

For example, the following command:

    ng generate component my-component

will generate few files:

    create src/main/webapp/app/my-component/my-component.component.html
    create src/main/webapp/app/my-component/my-component.component.ts
    update src/main/webapp/app/app.module.ts

## Building for production

### Packaging as jar

To build the final jar and optimize the jhipster application for production, run:

    ./mvnw -Pprod clean verify

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.jar

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./mvnw -Pprod,war clean verify

## Testing

To launch your application's tests, run:

    ./mvnw verify

### Client tests

Unit tests are run by [Jest][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

    npm test

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

or

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mysql database in a docker container, run:

    docker-compose -f src/main/docker/mysql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mysql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw -Pprod verify jib:dockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.
