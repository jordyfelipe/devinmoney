package devinmoney.entidades;

public enum TipoInvestimento {

	// Taxa a.a.
	CDB(7.4, "80% do CDI"), LCI(7.86, "85% do CDI");

	private Double taxa;
	private String descricao;

	TipoInvestimento(Double taxa, String descricao) {
		this.taxa = taxa;
		this.descricao = descricao;
	}

	public Double getTaxa() {
		return taxa;
	}

	public void setTaxa(Double taxa) {
		this.taxa = taxa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
