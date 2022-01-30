package devinmoney;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
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
	private Scanner scanner;

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
			System.out.println("Conta de investimento criada com sucesso!");
		} catch (EntradaIncorretaException e) {
			System.out.println(e.getMessage());
		}
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

	// inicio relatórios:
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
	}// fim relatorios

	public Optional<Conta> fazerLogin() {
		boolean leuCorretamente = false;
		Integer numeroConta = null;
		Integer senhaConta = null;

		while (!leuCorretamente) {
			scanner = new Scanner(System.in);
			try {
				System.out.println("Favor informar abaixo o número de sua conta:");
				numeroConta = scanner.nextInt();

				System.out.println("Favor informar abaixo a senha de sua conta:");
				senhaConta = scanner.nextInt();

				for (Conta conta : contas) {
					if (conta.getConta() == numeroConta && conta.getSenha() == senhaConta) {
						return Optional.of(conta);
					}
				}
				leuCorretamente = true;
			} catch (InputMismatchException e) {
				System.out.println("Entrada incorreta, favor informar um número inteiro!");
			}
		}
		return Optional.empty();
	}

	public void opcaoDepositar(Conta contaLogada) {
		boolean entradaCorreta = false;
		Double valorDeposito = null;
		while (!entradaCorreta) {
			scanner = new Scanner(System.in);
			try {
				System.out.println("Favor informar o valor abaixo:");
				valorDeposito = scanner.nextDouble();
				entradaCorreta = true;
			} catch (InputMismatchException e) {
				System.out.println("Valor inválido, tente novamente!");
			}
		}

		if (LocalDate.now().equals(getDataLocal())) {
			contaLogada.depositar(valorDeposito);
		} else {
			System.out.println("Data anterior a data hoje!");
		}

	}

	public void opcaoSacar(Conta contaLogada) {
		boolean entradaCorreta = false;
		Double valorSaque = null;
		while (!entradaCorreta) {
			scanner = new Scanner(System.in);
			try {
				System.out.println("Favor informar o valor abaixo:");
				valorSaque = scanner.nextDouble();
				entradaCorreta = true;
			} catch (InputMismatchException e) {
				System.out.println("Valor inválido, tente novamente!");
			}

		}
		if (LocalDate.now().equals(getDataLocal())) {
			contaLogada.sacar(valorSaque);
		} else {
			System.out.println("Data anterior a data hoje!");
		}
	}

	public void opcaoTransferir(Conta contaLogada) {
		Double valorTransferencia = null;
		Integer numeroContaDestino = null;
		boolean entradaCorreta = false;
		while (!entradaCorreta) {
			scanner = new Scanner(System.in);
			try {
				System.out.println("Favor informar o valor abaixo:");
				valorTransferencia = scanner.nextDouble();
				System.out.println("Favor informar o numero da conta destino abaixo:");
				numeroContaDestino = scanner.nextInt();
				entradaCorreta = true;
			} catch (InputMismatchException e) {
				System.out.println("Valor inválido, tente novamente!");
			}
		}

		if (LocalDate.now().equals(getDataLocal())) {
			if (verificarSeFinalDeSemana(LocalDate.now()) || LocalDate.now().isAfter(getDataLocal())) {
				System.out.println("Desculpe, não é possível realizar a operação em finais de semana.");
			} else {
				transferir(contaLogada, numeroContaDestino, valorTransferencia);
			}
		} else {
			System.out.println("Data anterior a data hoje!");
		}
	}

	public void opcaoSimularRendimentoPoupanca(Conta contaLogada) {
		boolean entradaCorreta = false;
		Integer periodo = null;
		Double taxa = null;
		while (!entradaCorreta) {
			scanner = new Scanner(System.in);
			try {
				System.out.println("Favor informar a qtde de meses abaixo:");
				periodo = scanner.nextInt();
				System.out.println("Favor informar a taxa anual abaixo:");
				taxa = scanner.nextDouble();
				entradaCorreta = true;
			} catch (InputMismatchException e) {
				System.out.println("Valor inválido, tente novamente!");
			}

		}

		System.out.println("Montante ao final de " + periodo + " meses: R$"
				+ ((ContaPoupanca) contaLogada).simularRendimento(periodo, taxa) + ".\n");
	}

	public void opcaoCriarContaInvestimento(Conta contaLogada) {
		Integer opcaoInvestimento = null;
		System.out.println(
				"Escolha umas das opções de investimento abaixo:\n" + "1- CDB " + TipoInvestimento.CDB.getDescricao()
						+ ", 2- LCI " + TipoInvestimento.LCI.getDescricao() + ", ou 3- Cancelar operação:");

		boolean entradaCorreta = false;
		while (!entradaCorreta) {
			scanner = new Scanner(System.in);
			try {
				opcaoInvestimento = scanner.nextInt();
				entradaCorreta = true;
			} catch (InputMismatchException e) {
				System.out.println("Opção inválida,\nEscolha umas das opções de investimento abaixo:\n" + "1- CDB "
						+ TipoInvestimento.CDB.getDescricao() + ", 2- LCI " + TipoInvestimento.LCI.getDescricao()
						+ ", ou 3- Cancelar operação:");
			}
		}

		switch (opcaoInvestimento) {
		case 1:
			criarContaInvestimento(contaLogada, TipoInvestimento.CDB);
			break;
		case 2:
			criarContaInvestimento(contaLogada, TipoInvestimento.LCI);
			break;
		case 3:
			System.out.println("Operação cancelada.");
			break;
		}
	}

	public void opcaoAlterarDadosCadastrais(Conta contaLogada) {
		boolean entradaCorreta = false;
		while (!entradaCorreta) {
			scanner = new Scanner(System.in);
			try {
				System.out.println("Favor informar novo nome abaixo:");
				String novoNome = scanner.next();
				System.out.println("Favor informar novo valor de renda mensal abaixo:");
				Double novaRenda = scanner.nextDouble();
				System.out.println("Favor informar nova senha abaixo:");
				Integer novaSenha = scanner.nextInt();
				contaLogada.alterarDadosCadastrais(novoNome, novaRenda, novaSenha);
				entradaCorreta = true;
			} catch (InputMismatchException e) {
				System.out.println("Valor inválido, tente novamente!");
			}
		}
	}

	public void opcaoSimularInvestimento(Conta contaLogada) {
		Integer periodo = null;
		boolean entradaCorreta = false;
		while (!entradaCorreta) {
			scanner = new Scanner(System.in);
			try {
				System.out.println("Favor informar a qtde de meses abaixo:");
				periodo = scanner.nextInt();
				entradaCorreta = true;
			} catch (InputMismatchException e) {
				System.out.println("Valor inválido, tente novamente!");
			}
		}

		System.out.println("Montante ao final de " + periodo + " meses: R$"
				+ ((ContaInvestimento) contaLogada).simularRendimento(periodo) + ".\n");
	}

	public void opcaoSair() {
		System.out.println("Você saiu.");
		System.exit(0);
	}

	public static void main(String[] args) {

		SistemaBancario sistemaBancario = new SistemaBancario();

		// mockInfo
		// numero conta = 1 / senha: 123;
		sistemaBancario.criarContaCorrente("Pedro", "047.289.008-25", 7000.00, Agencia.FLORIANOPOLIS, 5000.00, 123);
		// numero conta = 2 / senha: 123;
		sistemaBancario.criarContaCorrente("Maria", "027.258.051-13", 5000.00, Agencia.SAO_JOSE, -200.00, 123);
		// numero conta = 3 / senha: 123;
		sistemaBancario.criarContaPoupanca("João", "022.548.364-15", 3000.00, Agencia.SAO_JOSE, 3000.00, 123);
		// numero conta = 4 / senha: 123;
		sistemaBancario.criarContaInvestimento("Luana", "022.548.364-15", 8000.00, Agencia.FLORIANOPOLIS, 1000.00, 123,
				TipoInvestimento.CDB);
		// numero conta = 5 / senha: 123;
		sistemaBancario.criarContaInvestimento("Felipe", "022.548.364-15", 10000.00, Agencia.FLORIANOPOLIS, 2000.00,
				123, TipoInvestimento.LCI);

		boolean loginCorreto = false;

		while (!loginCorreto) {

			Scanner scanner = new Scanner(System.in);
			// inicio do Menu Usuario
			Optional<Conta> optionalConta;

			optionalConta = sistemaBancario.fazerLogin();

			if (optionalConta.isPresent()) {
				Conta contaLogada = optionalConta.get();
				Integer opcao = null;

				if (contaLogada instanceof ContaPoupanca) {
					loginCorreto = true;
					System.out.println("\nOlá " + contaLogada.getNome() + "!\nO que você deseja fazer? Abaixo opções:\n"
							+ "1- Depósito, 2- Saque, 3- Transferência, 4- Simular rendimento poupança, 5- Consultar extrato, 6- Criar conta de investimento, 7- Alterar dados cadastrais, 8- Sair do sistema");

					do {
						boolean leuCorretamente = false;
						while (!leuCorretamente) {
							scanner = new Scanner(System.in);
							try {
								opcao = scanner.nextInt();
								leuCorretamente = true;
							} catch (InputMismatchException e) {
								System.out.println(
										"Entrada incorreta, favor informar um número inteiro dentre as opções abaixo:\n"
												+ "1- Depósito, 2- Saque, 3- Transferência, 4- Simular rendimento poupança, 5- Consultar extrato, 6- Criar conta de investimento, 7- Alterar dados cadastrais, 8- Sair do sistema");
							}
						}

						switch (opcao) {

						case 1:
							sistemaBancario.opcaoDepositar(contaLogada);
							break;
						case 2:
							sistemaBancario.opcaoSacar(contaLogada);
							break;
						case 3:
							sistemaBancario.opcaoTransferir(contaLogada);
							break;
						case 4:
							sistemaBancario.opcaoSimularRendimentoPoupanca(contaLogada);
							break;
						case 5:
							System.out.println(contaLogada.getExtrato());
							break;
						case 6:
							sistemaBancario.opcaoCriarContaInvestimento(contaLogada);
							break;
						case 7:
							sistemaBancario.opcaoAlterarDadosCadastrais(contaLogada);
							break;
						case 8:
							sistemaBancario.opcaoSair();
							break;
						}
						System.out.println("\nO que deseja realizar a seguir? Abaixo opções:\n"
								+ "1- Depósito, 2- Saque, 3- Transferência, 4- Simular rendimento poupança, 5- Consultar extrato, 6- Criar conta de investimento, 7- Alterar dados cadastrais, 8- Sair do sistema");

					} while (opcao < 8);

					scanner.close();

				} else if (contaLogada instanceof ContaCorrente) {
					loginCorreto = true;
					System.out.println("\nOlá " + contaLogada.getNome() + "!\nO que você deseja fazer? Abaixo opções:\n"
							+ "1- Depósito, 2- Saque, 3- Transferência, 4- Criar conta de investimento, 5- Consultar extrato, 6- Alterar dados cadastrais, 7- Sair do sistema");

					do {
						boolean entradaCorreta = false;
						while (!entradaCorreta) {
							scanner = new Scanner(System.in);
							try {
								opcao = scanner.nextInt();
								entradaCorreta = true;
							} catch (InputMismatchException e) {
								System.out.println(
										"Entrada incorreta, favor informar um número inteiro dentre as opções abaixo:\n"
												+ "1- Depósito, 2- Saque, 3- Transferência, 4- Criar conta de investimento, 5- Consultar extrato, 6- Alterar dados cadastrais, 7- Sair do sistema");
							}
						}

						switch (opcao) {

						case 1:
							sistemaBancario.opcaoDepositar(contaLogada);
							break;

						case 2:
							sistemaBancario.opcaoSacar(contaLogada);
							break;
						case 3:
							sistemaBancario.opcaoTransferir(contaLogada);
							break;
						case 4:
							sistemaBancario.opcaoCriarContaInvestimento(contaLogada);
							break;
						case 5:
							System.out.println(contaLogada.getExtrato());
							break;
						case 6:
							sistemaBancario.opcaoAlterarDadosCadastrais(contaLogada);
							break;
						case 7:
							sistemaBancario.opcaoSair();
							break;
						}

						System.out.println("\nO que deseja realizar a seguir? Abaixo opções:\n"
								+ "1- Depósito, 2- Saque, 3- Transferência, 4- Criar conta de investimento, 5- Consultar extrato, 6- Alterar dados cadastrais, 7- Sair do sistema");

					} while (opcao < 7);

					scanner.close();
				} else {
					loginCorreto = true;
					System.out.println("\nOlá " + contaLogada.getNome() + "!\nO que você deseja fazer? Abaixo opções:\n"
							+ "1- Depósito, 2- Saque, 3- Transferência, 4- Simular investimento, 5- Consultar extrato, 6- Alterar dados cadastrais, 7- Sair do sistema");

					do {

						boolean entradaCorreta = false;
						while (!entradaCorreta) {
							scanner = new Scanner(System.in);
							try {
								opcao = scanner.nextInt();
								entradaCorreta = true;
							} catch (InputMismatchException e) {
								System.out.println(
										"Entrada incorreta, favor informar um número inteiro dentre as opções abaixo:\n"
												+ "1- Depósito, 2- Saque, 3- Transferência, 4- Simular investimento, 5- Consultar extrato, 6- Alterar dados cadastrais, 7- Sair do sistema");
							}
						}

						switch (opcao) {

						case 1:
							sistemaBancario.opcaoDepositar(contaLogada);
							break;
						case 2:
							sistemaBancario.opcaoSacar(contaLogada);
							break;
						case 3:
							sistemaBancario.opcaoTransferir(contaLogada);
							break;
						case 4:
							sistemaBancario.opcaoSimularInvestimento(contaLogada);
							break;
						case 5:
							System.out.println(contaLogada.getExtrato());
							break;
						case 6:
							sistemaBancario.opcaoAlterarDadosCadastrais(contaLogada);
							break;
						case 7:
							sistemaBancario.opcaoSair();
							break;
						}

						System.out.println("\nO que deseja realizar a seguir? Abaixo opções:\n"
								+ "1- Depósito, 2- Saque, 3- Transferência, 4- Simular investimento, 5- Consultar extrato, 6- Alterar dados cadastrais, 7- Sair do sistema");

					} while (opcao < 7);

				} // Fim do Menu do Usuário
			} else {
				System.out.println("Número da conta ou senha incorretos, favor tentar novamente!");
			}

		}

	}
}
