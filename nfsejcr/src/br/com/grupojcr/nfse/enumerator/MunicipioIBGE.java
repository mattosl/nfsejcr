package br.com.grupojcr.nfse.enumerator;

public enum MunicipioIBGE {
	CURITIBA(4106902L, "CURITIBA");
	
	private Long codigo;
	private String municipio;
	
	private MunicipioIBGE(Long codigo, String municipio) {
		this.codigo = codigo;
		this.municipio = municipio;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getMunicipio() {
		return municipio;
	}
	
	public static MunicipioIBGE obterPorCodigo(Long codigo) {
		if(codigo != null) {
			for(MunicipioIBGE mun : MunicipioIBGE.values()) {
				if(mun.getCodigo().equals(codigo)) {
					return mun;
				}
			}
		}
		return null;
	}

}
