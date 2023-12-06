package ChatRmi;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {//classe publica do servidor
public Server(){//construtor

    try {//bloco protegido
        Registry registry = LocateRegistry.createRegistry(1098);//local onde o servidor esta registrado
            ChatInterface server = new ChatServerImpl();//chatinterface server instancia/recebe um novo chatserverImpl
                Naming.rebind("rmi://localhost:1098/ChatInterface",server);
//Naming fornece métodos para armazenar e obter referências a objetos remotos em um registro de objeto 
//podendo ser associado a um nome usando o método rebind da classe Naming.
    } catch (Exception e){//bloco correposndente
    System.out.println("Exeção: " + e.getMessage());
}
}
public static void main (String args[]){
    Server server = new Server(); //Server instancia/recebe um novo server
    System.out.println("Servidor rodando! Rode o cliente!");//menssagem qua aparece na tela
}

}

