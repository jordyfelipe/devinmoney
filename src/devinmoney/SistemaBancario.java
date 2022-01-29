package devinmoney;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import devinmoney.entidades.Agencia;
import devinmoney.entidades.Conta;
import devinmoney.entidades.ContaCorrente;
import devinmoney.entidades.ContaInvestimento;
import devinmoney.entidades.ContaPoupanca;
import devinmoney.entidades.TipoInvestimento;
import devinmoney.entidades.Transacao;
import devinmoney.exceptions.EntradaIncorretaException;

public class SistemaBancario {

	private List<Conta> contas = new ArrayList<Conta>();
	private List<Transacao> transacoes = new ArrayList<Transacao>();
	private Integer numeroConta = 1;
	private LocalDate dataLocal;

	public SistemaBancario() {
		this.dataLocal = LocalDate.now();
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	public LocalDate getDataLocal() {
		return dataLocal;
	}

	public void setDataLocal(LocalDate dataLocal) {
		this.dataLocal = dataLocal;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	public void criarContaCorrente(String nome, String cpf, Double rendaMensal, Agencia agencia, Double saldo,
			Integer senha) {
		try {
			contas.add(new ContaCorrente(nome, cpf, rendaMensal, numeroConta, agencia, saldo, senha));
			numeroConta += 1;
		} catch (EntradaIncorretaException e) {
			System.out.println(e.getMessage());
		}
	}

	public void criarContaPoupanca(String nome, String cpf, Double rendaMensal, Agencia agencia, Double saldo,
			Integer senha) {
		try {
			contas.add(new ContaPoupanca(nome, cpf, rendaMensal, numeroConta, agencia, saldo, senha));
			numeroConta += 1;
		} catch (EntradaIncorretaException e) {
			System.out.println(e.getMessage());
		}
	}

	public void criarContaInvestimento(String nome, String cpf, Double rendaMensal, Agencia agencia, Double saldo,
			Integer senha, TipoInvestimento tipoInvestimento) {
		try {
			contas.add(new ContaInvestimento(nome, cpf, rendaMensal, numeroConta, agencia, saldo, senha,
					tipoInvestimento));
			numeroConta += 1;
		} catch (EntradaIncorretaException e) {
			System.out.println(e.getMessage());
		}
	}

	public void criarContaInvestimento(Conta contaLogada, TipoInvestimento tipoInvestimento) {
		try {
			contas.add(new ContaInvestimento(contaLogada.getNome(), contaLogada.getCpf(), contaLogada.getRendaMensal(),
					numeroConta, contaLogada.getAgencia(), 0.00, contaLogada.getSenha(), tipoInvestimento));
			numeroConta += 1;
		} catch (EntradaIncorretaException e) {
			System.out.println(e.getMessage());
		}
	}

	public Conta fazerLogin(Integer numeroConta, Integer senha) {
		Conta contaLogada = null;
		for (Conta conta : contas) {
			if (conta.getConta() == numeroConta && conta.getSenha() == senha) {
				contaLogada = conta;
			}
		}
		return contaLogada;
	}

	private Conta obterContaPorNumeroConta(Integer contaDestino) {
		for (Conta conta : contas) {
			if (conta.getConta() == contaDestino) {
				return conta;
			}
		}
		return null;
	}

	public void transferir(Conta contaLogada, Integer contaDestino, Double valor) {
		Conta conta = obterContaPorNumeroConta(contaDestino);
		Transacao transacao = new Transacao(contaLogada, conta, valor);
		transacoes.add(transacao);
		contaLogada.transferir(conta, valor);
	}

	public boolean verificarSeFinalDeSemana(LocalDate ld) {
		DayOfWeek dia = ld.getDayOfWeek();
		return dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
	}

	// Relatórios:
	public void listarTodasContas() {
		for (Conta conta : contas) {
			System.out.println(conta);
		}

	}

	public void listarContasCorrente() {
		List<Conta> contasCorrentes = contas.stream().filter(conta -> conta instanceof ContaCorrente)
				.collect(Collectors.toList());
		for (Conta contaCorrente : contasCorrentes) {
			System.out.println(contaCorrente);
		}
	}

	public void listarContasPoupanca() {
		List<Conta> contasCorrentes = contas.stream().filter(conta -> conta instanceof ContaPoupanca)
				.collect(Collectors.toList());
		for (Conta contaCorrente : contasCorrentes) {
			System.out.println(contaCorrente);
		}
	}

	public void listarContasInvestimento() {
		List<Conta> contasCorrentes = contas.stream().filter(conta -> conta instanceof ContaInvestimento)
				.collect(Collectors.toList());
		for (Conta contaCorrente : contasCorrentes) {
			System.out.println(contaCorrente);
		}
	}

	public void listarContasSaldoNegativo() {
		List<Conta> contasNegativas = contas.stream().filter(conta -> conta.getSaldo() < 0.0)
				.collect(Collectors.toList());
		for (Conta conta : contasNegativas) {
			System.out.println(conta);
		}
	}

	public void listarTotalInvestido() {
		List<Conta> contasInvestimento = contas.stream().filter(conta -> conta instanceof ContaInvestimento)
				.collect(Collectors.toList());
		Double totalInvestido = 0.0;
		for (Conta conta : contasInvestimento) {
			totalInvestido += conta.getSaldo();
		}
		System.out.println(totalInvestido);
	}

	public void listarTransferenciasCliente(Integer conta) {
		List<Transacao> transacoesCliente = transacoes.stream()
				.filter(transacao -> (transacao.getContaOrigem().getConta() == conta)).collect(Collectors.toList());
		for (Transacao transacao : transacoesCliente) {
			System.out.println(transacao);
		}
	}

	public void listarExtratoCliente(Integer conta) {
		for (Conta contaCliente : contas) {
			if (contaCliente.getConta() == conta) {
				System.out.println(contaCliente.getExtrato());
			}
		}
	}

	public static void main(String[] args) {

		SistemaBancario sistemaBancario = new SistemaBancario();

		// mockInfo
		// numero conta = 1 / senha: 123;
		sistemaBancario.criarContaCorrente("Pedro", "047.289.008-25", 7000.00, Agencia.FLORIANOPOLIS, 5000.00, 123);
		// numero conta = 2 / senha: 123;
		sistemaBancario.criarContaCorrente("Maria", "027.258.051-13", 2000.00, Agencia.SAO_JOSE, 200.00, 123);
		// numero conta = 3 / senha: 123;
		sistemaBancario.criarContaPoupanca("João", "022.548.364-15", 3000.00, Agencia.SAO_JOSE, 3000.00, 123);
		// numero conta = 4 / senha: 123;
		sistemaBancario.criarContaInvestimento("Luana", "022.548.364-15", 8000.00, Agencia.FLORIANOPOLIS, 1000.00, 123,
				TipoInvestimento.CDB);
		// numero conta = 5 / senha: 123;
		sistemaBancario.criarContaInvestimento("Felipe", "022.548.364-15", 10000.00, Agencia.FLORIANOPOLIS, 2000.00,
				123, TipoInvestimento.LCI);

		// Inicio do Menu Usuário
		Scanner scanner = new Scanner(System.in);
		System.out.println("Favor informar abaixo o número de sua conta:");
		Integer numeroConta = scanner.nextInt();

		System.out.println("Favor informar abaixo a senha de sua conta:");
		Integer senhaConta = scanner.nextInt();

		Conta contaLogada = sistemaBancario.fazerLogin(numeroConta, senhaConta);

		Integer opcao;

		if (contaLogada instanceof ContaPoupanca) {

			System.out.println("\nOlá " + contaLogada.getNome() + "!\nO que você deseja fazer? Abaixo opções:\n"
					+ "1- Depósito, 2- Saque, 3- Transferência, 4- Simular rendimento poupança, 5- Consultar extrato, 6- Criar conta de investimento, 7- Alterar dados cadastrais, 8- Sair do sistema");

			do {

				opcao = scanner.nextInt();

				switch (opcao) {

				case 1:
					System.out.println("Favor informar o valor abaixo:");
					Double valorDeposito = scanner.nextDouble();
					if (LocalDate.now().equals(sistemaBancario.getDataLocal())) {
						contaLogada.depositar(valorDeposito);
					} else {
						System.out.println("Data anterior a data hoje!");
					}
					break;
				case 2:
					System.out.println("Favor informar o valor abaixo:");
					Double valorSaque = scanner.nextDouble();
					if (LocalDate.now().equals(sistemaBancario.getDataLocal())) {
						contaLogada.sacar(valorSaque);
					} else {
						System.out.println("Data anterior a data hoje!");
					}
					break;
				case 3:
					System.out.println("Favor informar o valor abaixo:");
					Double valorTransferencia = scanner.nextDouble();
					System.out.println("Favor informar o numero da conta destino abaixo:");
					Integer numeroContaDestino = scanner.nextInt();
					if (LocalDate.now().equals(sistemaBancario.getDataLocal())
							|| LocalDate.now().isAfter(sistemaBancario.getDataLocal())) {
						if (sistemaBancario.verificarSeFinalDeSemana(LocalDate.now())) {
							System.out.println("Desculpe, não é possível realizar a operação em finais de semana.");
						} else {
							sistemaBancario.transferir(contaLogada, numeroContaDestino, valorTransferencia);
						}
					} else {
						System.out.println("Data anterior a data hoje!");
					}

					break;
				case 4:
					System.out.println("Favor informar a qtde de meses abaixo:");
					Integer periodo = scanner.nextInt();
					System.out.println("Favor informar a taxa anual abaixo:");
					Double taxa = scanner.nextDouble();
					System.out.println("Montante ao final de " + periodo + " meses: R$"
							+ ((ContaPoupanca) contaLogada).simularRendimento(periodo, taxa) + ".\n");
					break;
				case 5:
					System.out.println(contaLogada.getExtrato());
					break;
				case 6:
					Integer opcaoInvestimento;
					System.out.println("Escolha umas das opções de investimento abaixo:\n" + "1- CDB "
							+ TipoInvestimento.CDB.getDescricao() + ", 2- LCI " + TipoInvestimento.LCI.getDescricao()
							+ ", ou 3- Cancelar operação:");
					opcaoInvestimento = scanner.nextInt();
					switch (opcaoInvestimento) {
					case 1:
						sistemaBancario.criarContaInvestimento(contaLogada, TipoInvestimento.CDB);
						break;
					case 2:
						sistemaBancario.criarContaInvestimento(contaLogada, TipoInvestimento.LCI);
						break;
					case 3:
						System.out.println("Operação cancelada.");
						break;
					}
					break;
				case 7:
					System.out.println("Favor informar novo nome abaixo:");
					String novoNome = scanner.next();
					System.out.println("Favor informar novo valor de renda mensal abaixo:");
					Double novaRenda = scanner.nextDouble();
					System.out.println("Favor informar nova senha abaixo:");
					Integer novaSenha = scanner.nextInt();
					contaLogada.alterarDadosCadastrais(novoNome, novaRenda, novaSenha);
					break;
				case 8:
					System.out.println("Você saiu.");
					System.exit(0);
					break;
				}
				System.out.println("\nO que deseja realizar a seguir? Abaixo opções:\n"
						+ "1- Depósito, 2- Saque, 3- Transferência, 4- Simular rendimento poupança, 5- Consultar extrato, 6- Criar conta de investimento, 7- Alterar dados cadastrais, 8- Sair do sistema");

			} while (opcao < 7);

			scanner.close();

		} else if (contaLogada instanceof ContaCorrente) {

			System.out.println("\nOlá " + contaLogada.getNome() + "!\nO que você deseja fazer? Abaixo opções:\n"
					+ "1- Depósito, 2- Saque, 3- Transferência, 4- Criar conta de investimento, 5- Consultar extrato, 6- Alterar dados cadastrais, 7- Sair do sistema");

			do {

				opcao = scanner.nextInt();

				switch (opcao) {

				case 1:
					System.out.println("Favor informar o valor abaixo:");
					Double valorDeposito = scanner.nextDouble();
					if (LocalDate.now().equals(sistemaBancario.getDataLocal())) {
						contaLogada.depositar(valorDeposito);
					} else {
						System.out.println("Data anterior a data hoje!");
					}
					break;

				case 2:
					System.out.println("Favor informar o valor abaixo:");
					Double valorSaque = scanner.nextDouble();
					if (LocalDate.now().equals(sistemaBancario.getDataLocal())) {
						contaLogada.sacar(valorSaque);
					} else {
						System.out.println("Data anterior a data hoje!");
					}
					break;
				case 3:
					System.out.println("Favor informar o valor abaixo:");
					Double valorTransferencia = scanner.nextDouble();
					System.out.println("Favor informar o numero da conta destino abaixo:");
					Integer numeroContaDestino = scanner.nextInt();
					if (LocalDate.now().equals(sistemaBancario.getDataLocal())
							|| LocalDate.now().isAfter(sistemaBancario.getDataLocal())) {
						if (sistemaBancario.verificarSeFinalDeSemana(LocalDate.now())) {
							System.out.println("Desculpe, não é possível realizar a operação em finais de semana.");
						} else {
							sistemaBancario.transferir(contaLogada, numeroContaDestino, valorTransferencia);
						}
					} else {
						System.out.println("Data anterior a data hoje!");
					}

					break;
				case 4:
					Integer opcaoInvestimento;
					System.out.println("Escolha umas das opções de investimento abaixo:\n" + "1- CDB "
							+ TipoInvestimento.CDB.getDescricao() + ", 2- LCI " + TipoInvestimento.LCI.getDescricao()
							+ ", ou 3- Cancelar operação:");
					opcaoInvestimento = scanner.nextInt();
					switch (opcaoInvestimento) {
					case 1:
						sistemaBancario.criarContaInvestimento(contaLogada, TipoInvestimento.CDB);
						break;
					case 2:
						sistemaBancario.criarContaInvestimento(contaLogada, TipoInvestimento.LCI);
						break;
					case 3:
						System.out.println("Operação cancelada.");
						break;
					}
					break;
				case 5:
					System.out.println(contaLogada.getExtrato());
					break;
				case 6:
					System.out.println("Favor informar novo nome abaixo:");
					String novoNome = scanner.next();
					System.out.println("Favor informar novo valor de renda mensal abaixo:");
					Double novaRenda = scanner.nextDouble();
					System.out.println("Favor informar nova senha abaixo:");
					Integer novaSenha = scanner.nextInt();
					contaLogada.alterarDadosCadastrais(novoNome, novaRenda, novaSenha);
					break;
				case 7:
					System.out.println("Você saiu.");
					System.exit(0);
					break;
				}

				System.out.println("\nO que deseja realizar a seguir? Abaixo opções:\n"
						+ "1- Depósito, 2- Saque, 3- Transferência, 4- Criar conta de investimento, 5- Consultar extrato, 6- Alterar dados cadastrais, 7- Sair do sistema");

			} while (opcao < 6);

			scanner.close();
		} else {

			System.out.println("\nOlá " + contaLogada.getNome() + "!\nO que você deseja fazer? Abaixo opções:\n"
					+ "1- Depósito, 2- Saque, 3- Transferência, 4- Simular investimento, 5- Consultar extrato, 6- Alterar dados cadastrais, 7- Sair do sistema");

			do {

				opcao = scanner.nextInt();

				switch (opcao) {

				case 1:
					System.out.println("Favor informar o valor abaixo:");
					Double valorDeposito = scanner.nextDouble();
					if (LocalDate.now().equals(sistemaBancario.getDataLocal())) {
						contaLogada.depositar(valorDeposito);
					} else {
						System.out.println("Data anterior a data hoje!");
					}
					break;
				case 2:
					System.out.println("Favor informar o valor abaixo:");
					Double valorSaque = scanner.nextDouble();
					if (LocalDate.now().equals(sistemaBancario.getDataLocal())) {
						contaLogada.sacar(valorSaque);
					} else {
						System.out.println("Data anterior a data hoje!");
					}
					break;
				case 3:
					System.out.println("Favor informar o valor abaixo:");
					Double valorTransferencia = scanner.nextDouble();
					System.out.println("Favor informar o numero da conta destino abaixo:");
					Integer numeroContaDestino = scanner.nextInt();
					if (LocalDate.now().equals(sistemaBancario.getDataLocal())) {
						if (sistemaBancario.verificarSeFinalDeSemana(LocalDate.now())
								|| LocalDate.now().isAfter(sistemaBancario.getDataLocal())) {
							System.out.println("Desculpe, não é possível realizar a operação em finais de semana.");
						} else {
							sistemaBancario.transferir(contaLogada, numeroContaDestino, valorTransferencia);
						}
					} else {
						System.out.println("Data anterior a data hoje!");
					}

					break;
				case 4:
					System.out.println("Favor informar a qtde de meses abaixo:");
					Integer periodo = scanner.nextInt();
					System.out.println("Montante ao final de " + periodo + " meses: R$"
							+ ((ContaInvestimento) contaLogada).simularRendimento(periodo) + ".\n");
					break;
				case 5:
					System.out.println(contaLogada.getExtrato());
					break;
				case 6:
					System.out.println("Favor informar novo nome abaixo:");
					String novoNome = scanner.next();
					System.out.println("Favor informar novo valor de renda mensal abaixo:");
					Double novaRenda = scanner.nextDouble();
					System.out.println("Favor informar nova senha abaixo:");
					Integer novaSenha = scanner.nextInt();
					contaLogada.alterarDadosCadastrais(novoNome, novaRenda, novaSenha);

					break;
				case 7:
					System.out.println("Você saiu.");
					System.exit(0);
					break;
				}

				System.out.println("\nO que deseja realizar a seguir? Abaixo opções:\n"
						+ "1- Depósito, 2- Saque, 3- Transferência, 4- Simular investimento, 5- Consultar extrato, 6- Alterar dados cadastrais, 7- Sair do sistema");

			} while (opcao < 6);
		} // Fim do Menu do Usuário

	}
}
