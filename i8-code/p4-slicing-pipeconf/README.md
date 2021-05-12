# P4 Slicer Pipeconf

## How to compile

1. Compile P4

```bash
cd src/main/resources
make
cd -
```

You need first to have installed the P4 dependencies to be able to compile the .p4 file:

`curl -s https://raw.githubusercontent.com/opennetworkinglab/onos/master/tools/dev/p4vm/install-p4-tools.sh| bash`

2. Compile Java application with p4 bundled

`mvn clean install`
