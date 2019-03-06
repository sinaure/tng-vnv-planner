# Planner 5GTANGO Verification and Validation
This is a [5GTANGO](http://www.5gtango.eu) component to coordinate the verification and validation activities of 5G Network Services.


<p align="center"><img src="https://github.com/sonata-nfv/tng-api-gtw/wiki/images/sonata-5gtango-logo-500px.png" /></p>

## What it is

The Planner acts as the main manager for all V&V test requests. It is responsible for test plans management, sequencing, and triggering requests of the corresponding test results. Although the Planner is responsible for overall test activity, execution of individual test plans is given over to the curator and executor.
An outline of the architecture is shown below.

<p align="center"><img src="https://user-images.githubusercontent.com/173755/53358658-d5cd7e00-3928-11e9-8acb-bc535fd7df18.png" /></p>

## Build from source code

```bash
./gradlew
```

The project depends on java and docker to build. Once you have those two tools, you simply run `./gradlew` command without parameter to do a full build:
* clean : clean the project build directory
* compile code
* process resources
* package jar
* compile test
* process test resources
* execute test
* execute docker build
* execute docker push: optional, default to
  * `true` on jenkins build
  * `false` on local build

For advanced build arguments, please checkout the [gradle-buildscript](https://github.com/mrduguo/gradle-buildscript) project.


## Run the docker image

```bash
docker pull registry.sonata-nfv.eu:5000/tng-vnv-planner
docker run -d --name tng-vnv-planner -p 6100:6100 registry.sonata-nfv.eu:5000/tng-vnv-planner
```

### Health checking

Once the component finish start, you can access (change IP depends on your docker setup) the component health endpoint at:

http://locahost:6100/tng-vnv-planner/health

### Swagger UI

* static
    * http://petstore.swagger.io/?url=https://raw.githubusercontent.com/sonata-nfv/tng-vnv-planner/master/src/main/resources/static/swagger.json
    * http://petstore.swagger.io/?url=https://raw.githubusercontent.com/sonata-nfv/tng-vnv-planner/master/src/main/resources/static/swagger-dependencies.json
* pre integration 
    * http://pre-int-vnv-ave.5gtango.eu:6100/tng-vnv-planner/swagger-ui.html
* local 
    * http://localhost:6100/tng-vnv-planner/swagger-ui.html
    * http://localhost:6100/tng-vnv-planner/swagger-ui.html?urls.primaryName=dependencies



## Dependencies

No specific libraries are required for building this project. The following tools are used to build the component

- `Java JDK (8+)`
- `gradle (4.9)`
- `docker (18.x)`


## Contributing
Contributing to the Gatekeeper is really easy. You must:

1. Clone [this repository](http://github.com/sonata-nfv/tng-vnv-planner);
1. Work on your proposed changes, preferably through submiting [issues](https://github.com/sonata-nfv/tng-vnv-planner/issues);
1. Submit a Pull Request;
1. Follow/answer related [issues](https://github.com/sonata-nfv/tng-vnv-planner/issues) (see Feedback, below).


## License

This 5GTANGO component is published under Apache 2.0 license. Please see the [LICENSE](LICENSE) file for more details.

## Lead Developers

The following lead developers are responsible for this repository and have admin rights. They can, for example, merge pull requests.

* George Andreou ([allemaos](https://github.com/allemaos))
* Felipe Vicens ([felipevicens](https://github.com/felipevicens))

## Feedback

Please use the [GitHub issues](https://github.com/sonata-nfv/tng-vnv-planner/issues) and the 5GTANGO Verification and Validation group mailing list `5gtango-dev@list.atosresearch.eu` for feedback.

