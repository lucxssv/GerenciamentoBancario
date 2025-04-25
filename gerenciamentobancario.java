package com.mycompany.gerenciabanco;

/**
 *
 * @author Lucas Santos vieira
 */

import java.util.Scanner; 
import java.util.ArrayList;
import java.util.List;

class Cliente { //Representa um cliente do banco
    private String nome;
    private String sobrenome;
    private String cpf;
    
    public Cliente(String nome, String sobrenome, String cpf){
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
    }

    public String getNomeCompleto(){
        return nome + " " + sobrenome;
    }
    
    public String getCpf(){
        return cpf;
    }
} //Fim da classe Cliente

class ContaBancaria { //Representa a conta bancária do cliente e as ações quais ele pode executar
    private Cliente cliente;
    private double saldo;
    
    public ContaBancaria(Cliente cliente) {
        this.cliente = cliente;
        this.saldo = 0.0;
    }
    
    public void consultarSaldo() {
        System.out.printf("Saldo atual: R$ %.2f%n", saldo);
    }
    
    public void depositar(double valor) {
        if (valor >0) {
            saldo += valor;
            System.out.printf("Deposito de R$ %.2f realizado com sucesso.%n", valor);
        } else { 
            System.out.println("Valor invalido para deposito");
        }
    }
    
    public void sacar(double valor) {
        if (valor >0 && valor <= saldo) {
            saldo -= valor;
            System.out.printf("Saque de R$ %.2f realizado com sucesso.%n", valor);
        } else {
            System.out.println("Saque invalido. Verifique o valor e o saldo disponivel");
        }
    }
    
    public Cliente getCliente() {
        return cliente;
    }
} //Fim da Classe ContaBancaria

public class GerenciaBanco { //O programa começa aqui, pelo método main na linha de baixo
    public static void main(String[] args) {
            
        Scanner scanner = new Scanner(System.in);
        List<ContaBancaria> contas = new ArrayList<>();

        int opcao;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Criar nova conta");
            System.out.println("2 - Acessar conta existente");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // consome a quebra de linha

            switch (opcao) {
                case 1:
                    System.out.print("Digite seu nome: ");
                    String nome = scanner.nextLine();
                    
                    System.out.print("Digite seu sobrenome: ");
                    String sobrenome = scanner.nextLine();
                    
                    String cpf;
                    do {
                        System.out.print("Digite seu CPF (com ou sem pontuacao): ");
                        cpf = scanner.nextLine().replaceAll("[^\\d]", "");
                        
                        if (cpf.length() != 11) {
                            System.out.println("Cpf invalido! Ele deve conter exatamente 11 digitos numericos");
                        }
                    } while (cpf.length() != 11);
                    
                    boolean cpfExistente = false;
                    for (ContaBancaria conta :contas) {
                        if (conta.getCliente().getCpf().equals(cpf)) {
                            cpfExistente = true;
                            break;
                        }
                    }
                    
                    if (cpfExistente) {
                        System.out.println("ja existe uma conta cadastrada com esse CPF.");
                    } else {
                        Cliente novoCliente = new Cliente(nome,sobrenome, cpf);
                        ContaBancaria novaConta = new ContaBancaria(novoCliente);
                        contas.add(novaConta);
                        System.out.println("Conta criada com sucesso para " + novoCliente.getNomeCompleto() + "!");
                    }
                    
                    break;
                    
                case 2:
                    System.out.print("Digite o CPF da conta (com ou sem pontuacao): ");
                    String cpfBusca = scanner.nextLine().replaceAll("[^\\d]", "");
                    
                    ContaBancaria contaEncontrada = null;
                    for (ContaBancaria conta : contas) {
                        if (conta.getCliente().getCpf().equals(cpfBusca)) {
                            contaEncontrada = conta;
                            break;
                        }
                    }
                    
                    if (contaEncontrada != null) {
                        int opcaoConta;
                        do {
                            System.out.println("\n--- Conta de " + contaEncontrada.getCliente().getNomeCompleto() + "---");
                            System.out.println("1 - Consultar saldo");
                            System.out.println("2 - Depositar");
                            System.out.println("3 - Sacar");
                            System.out.println("4 - Voltar ao menu principal");
                            System.out.println("Escolha uma opcao: ");
                            opcaoConta = scanner.nextInt();
                            
                            switch (opcaoConta) {
                                
                                case 1:
                                    contaEncontrada.consultarSaldo();
                                    break;
                                    
                                case 2:
                                    System.out.print("Valor para deposito: ");
                                    double deposito = scanner.nextDouble();
                                    contaEncontrada.depositar(deposito);
                                    break;
                                    
                                case 3:
                                    System.out.print("Valor para saque: ");
                                    double saque = scanner.nextDouble();
                                    contaEncontrada.sacar(saque);
                                    break;
                                    
                                case 4:
                                    System.out.println("Voltando para o menu principal...");
                                    break;
                                    
                                default:
                                    System.out.println("Opcao invalida. Tente Novamente.");
                            }
                            
                        } while (opcaoConta != 4);
                        
                        scanner.nextLine(); //Limpar quebra de linha
                        
                    } else {
                        
                        System.out.println("Conta nao encontrada para o CPF informado");
                    }
                    
                    break;
                    
                case 3:
                    System.out.println("Encerrando o sistema...");
                    break;
                    
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }

        } while (opcao != 3);

        scanner.close();  
    }
}
