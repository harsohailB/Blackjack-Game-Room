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

5. Change directory to project directory (either client or server)

```
cd Blackjack-Game-Room/Client
cd Blackjack-Game-Room/Server
```

6. Compile all java files to create .class files

```
javac *.java
```

7. Run java program using the .class file that contains the main funciton

```
java ServerController
java ClientCommnicationController
```

8. Program running!