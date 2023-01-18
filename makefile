#
# Help/Usage
#

C_BLUE := "\\033[94m"
C_NONE := "\\033[0m"

.PHONY: default
default:
	@echo "Please choose one of:"
	@echo ""
	@echo "$(C_BLUE)  make install-dev-env$(C_NONE)"
	@echo "    Checks the development environment for initial build dependencies "
	@echo "    and downloads them if necessary."
	@echo ""
	@echo "$(C_BLUE)  make raml-gen-code$(C_NONE)"
	@echo "    Generates Java classes representing API interfaces as "
	@echo "    defined in api.raml and child types."
	@echo ""
	@echo "$(C_BLUE)  make compile$(C_NONE)"
	@echo "    Compiles the existing code in 'src/'.  Regenerates files if the"
	@echo "    api spec has changed."
	@echo ""
	@echo "$(C_BLUE)  make test$(C_NONE)"
	@echo "    Compiles the existing code in 'src/' and runs unit tests."
	@echo "    Regenerates files if the api spec has changed."
	@echo ""
	@echo "$(C_BLUE)  make jar$(C_NONE)"
	@echo "    Compiles a 'fat jar' from this project and its dependencies."
	@echo ""
	@echo "$(C_BLUE)  make docker$(C_NONE)"
	@echo "    Builds a runnable docker image for this service"
	@echo ""
	@echo "$(C_BLUE)  make clean$(C_NONE)"
	@echo "    Remove files generated by other targets; put project back in"
	@echo "    its original state."
	@echo ""

#
# Development environment setup / teardown
#

.PHONY: install-dev-env
install-dev-env:
	./gradlew check-env

.PHONY: clean
clean:
	@./gradlew clean
	@rm -rf .bin .gradle

#
# Code & Doc Generation
#

.PHONY: raml-gen-code
raml-gen-code:
	./gradlew generate-jaxrs

.PHONY: raml-gen-docs
raml-gen-docs:
	./gradlew generate-raml-docs

#
# Build & Test Targets
#

.PHONY: compile
compile:
	./gradlew clean compileJava

.PHONY: test
test:
	./gradlew clean test

.PHONY: jar
jar: build/libs/service.jar

.PHONY: docker
docker:
	./gradlew build-docker --stacktrace

#
# File based targets
#

build/libs/service.jar: build.gradle.kts
	./gradlew clean test generate-raml-docs shadowJar
