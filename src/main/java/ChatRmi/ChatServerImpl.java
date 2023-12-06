package ChatRmi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

//a classe ChatServerImpl extende java.rmi.server.UnicastRemoteObject e implementa a classe ChatInterface
public class ChatServerImpl extends java.rmi.server.UnicastRemoteObject implements ChatInterface {
    ArrayList<String> mensagens;//percorre, cria e mantém uma lista percorrendo a string menssagem <>percorre
    int nMensagens; //variavel que contabiliza n mensagens

public ChatServerImpl() throws RemoteException { //lança como metodo remoto
    super();//chama o construtor da superclasse
    this.mensagens = new ArrayList<>();//referencia o objeto menssage que recebe um novo arraylist
}
//classe publica que envia menssagem, passando a mensagem por uma string que lança no metodo remoto
    @Override
    public void enviarMensagem(String mensagem) throws RemoteException{
    mensagens.add(mensagem);//
}
//classe publica de um array, passando a mensagem por uma string que lança no metodo remoto
    @Override
    public ArrayList<String> lerMensagem() throws RemoteException{
    return mensagens;//retorna as mensagens
}

}