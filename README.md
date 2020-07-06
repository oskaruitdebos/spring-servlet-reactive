# Introduction 
This is a simple Git repo to do a quick performance comparison between the Spring servlet stack and the Spring reactive stack.

The context of the comparison:
- Both run Java 14, in Docker and are limited to 512mb memory.
- Both get hit with a simple query to a MongoDB back-end, which returns a small dataset. 
- The MongoDB containers responds with 400ms simulated network latency. Normaliy network latency won't be nearly this high. Setting it this high allows me simulare a heavier query without having to build a more comprehensive dataset.

# How to set it up

## Step one: running the containers:

```
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

cd java_servlet
./gradlew build bootJar
docker build -t java-servlet .
cd ..

cd java_reactive
./gradlew build bootJar
docker build -t java-reactive .
cd ..

docker-compose -f support/docker-compose-combined.yaml up -d
```

## Step two: Setting up the network latency

```
docker exec mongo apt-get update
docker exec mongo apt-get install iproute2 iputils-ping -y
docker exec mongo tc qdisc add dev eth0 root netem delay 400ms
```

## Step three: Inserting some sample data
```
curl http://localhost:8080/v1/quote -H 'Content-type:application/json' -d ' { "quote": "this is a first quote" }'
curl http://localhost:8080/v1/quote -H 'Content-type:application/json' -d ' { "quote": "this is a second quote" }'
curl http://localhost:8080/v1/quote -H 'Content-type:application/json' -d ' { "quote": "this is a third quote" }'
```

## Step four: Running the load test
There is a sample Jmeter file in the support folder which you can run like this if you have Jmeter installed (available through Homebrew):

```
jmeter -n -t ./support/TestPlan.jmx
```
The Jmeter test will output a csv called: "summary_output.csv"

You can keep an eye on resource consumption of the containers with:
```
docker stats
```