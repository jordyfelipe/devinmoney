package devinmoney.entidades;

import devinmoney.exceptions.EntradaIncorretaException;

public class ContaInvestimento extends Conta {

	public ContaInvestimento(String nome, String cpf, Double rendaMensal, Integer conta, Agencia agencia, Double saldo,
			Integer senha, TipoInvestimento tipoInvestimento) throws EntradaIncorretaException {
		super(nome, cpf, rendaMensal, conta, agencia, saldo, senha);
		this.tipoInvestimento = tipoInvestimento;
	}

	TipoInvestimento tipoInvestimento;

	public String simularRendimento(Integer periodo) {
		Double montante = 0.00;
		Double taxa = 0.00;
		// Converte taxa anual em mensal
		taxa = (Math.pow(1.0 + (this.tipoInvestimento.getTaxa() / 100), 1.0 / 12.0) - 1);
		// formata para 4 casas depois da vírgula
		taxa = formatarCasas(taxa, 4);
		for (int mes = 1; mes <= periodo; ++mes) {
			// calcula novo montante durante mês especificado
			montante = this.saldo * Math.pow(1.0 + taxa, mes);
		}
		return String.format("%.2f", montante);

	}

	private Double formatarCasas(Double valor, int qtdCasas) {
		Double fator = Math.pow(10, qtdCasas);
		return (Math.floor(valor * fator) / fator);
	}

	@Override
	public void sacar(Double valor) {
		if (this.saldo >= valor) {
			this.saldo -= valor;
			this.setExtrato(DescricaoExtrato.SAQUE, valor);
		} else {
			System.out.println("Não foi possível realizar a operação " + this.nome + ", saldo insuficiente.");
		}
	}

	@Override
	public void transferir(Conta contaDestino, Double valor) {
		if (this.saldo >= valor) {
			this.saldo -= valor;
			contaDestino.setSaldo(contaDestino.getSaldo() + valor);
			this.setExtrato(DescricaoExtrato.TRANSFERENCIA_EFETUADA, valor, contaDestino.nome);
			contaDestino.setExtrato(DescricaoExtrato.TRANSFERENCIA_RECEBIDA, valor, this.nome);
		} else {
			System.out.println("Não foi possível realizar a operação " + this.nome + ", saldo insuficiente.");
		}
	}

}
