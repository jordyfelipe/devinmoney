package devinmoney.entidades;

public enum DescricaoExtrato {

	DEPOSITO("Deposito recebido"), SAQUE("Saque efetuado"), TRANSFERENCIA_EFETUADA("Transferencia efetuada"),
	TRANSFERENCIA_RECEBIDA("Transferencia recebida");

	private String descricao;

	private DescricaoExtrato(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
