# Compiling Project on RAC

1. Set up 3 ubuntu virtual machines on the in RAC
2. Access the ubuntu virtual machines from the terminal of your local PC:

```
ssh ubuntu@(ip address of VM)
```

3. Install GIT libraries on the VMs to clone the project

```
sudo apt-get install git-core
```

4. Clone the project 

```
git clone https://github.com/harsohailB/Blackjack-Game-Room.git
```

5. Change directory to the directory which contains the .jar file(either client or server)

```
Server:
cd /home/ubuntu/Blackjack-Game-Room/Server/out/artifacts/Server_jar
Client:
cd /home/ubuntu/Blackjack-Game-Room/Client/out/artifacts/Client_jar
```

6. Run the jar file

```
Server:
java -jar Server.jar
Client:
java -jar Client.jar
```

7. Program running!

### Note 

**Server:** the server program prints its IP when you run the program

**Client:** the client program prompts for the server when you the program

* Please enter the server given IP to the client to connect!
* If server is running on the same machine as the client, 'localhost' can be used instead of IP of server
