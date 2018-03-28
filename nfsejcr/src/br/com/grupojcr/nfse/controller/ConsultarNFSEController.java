package br.com.grupojcr.nfse.controller;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import br.com.grupojcr.nfse.entity.xml.NfseXML;

@Named
@ViewScoped
public class ConsultarNFSEController implements Serializable {

	private static final long serialVersionUID = 764194435849716691L;
	
	private Date dtInicio;
	
	private List<String> teste = new ArrayList<String>(Arrays.asList("teste", "teste", "teste", "teste"));
	private List<String> testeSelecionados = new ArrayList<String>();
	
	public String iniciarIncluir() {
		try {
			String xml = 
					"<Nfse>" +
						"<InfNfse>" +
							"<Numero>1621</Numero>" +
							"<CodigoVerificacao>MSGJA80H</CodigoVerificacao>" +
							"<DataEmissao>2018-03-01T00:00:00</DataEmissao>" +
							"<IdentificacaoRps>" +
								"<Numero>169</Numero>" +
								"<Serie>SN</Serie>" +
								"<Tipo>1</Tipo>" +
							"</IdentificacaoRps>" +
							"<DataEmissaoRps>2018-03-01T00:00:00</DataEmissaoRps>" +
							"<NaturezaOperacao>1</NaturezaOperacao>" +
							"<RegimeEspecialTributacao>0</RegimeEspecialTributacao>" +
							"<OptanteSimplesNacional>2</OptanteSimplesNacional>" +
							"<IncentivadorCultural>2</IncentivadorCultural>" +
							"<Competencia>0001-01-01T00:00:00</Competencia>" +
							"<NfseSubstituida>0</NfseSubstituida>" +
							"<Servico>" +
								"<Valores>" +
									"<ValorServicos>1203.20</ValorServicos>" +
									"<ValorDeducoes>0.00</ValorDeducoes>" +
									"<ValorPis>7.82</ValorPis>" +
									"<ValorCofins>36.10</ValorCofins>" +
									"<ValorInss>0.00</ValorInss>" +
									"<ValorIr>18.05</ValorIr>" +
									"<ValorCsll>12.03</ValorCsll>" +
									"<IssRetido>2</IssRetido>" +
									"<ValorIss>60.16</ValorIss>" +
									"<ValorIssRetido>0.00</ValorIssRetido>" +
									"<OutrasRetencoes>0.00</OutrasRetencoes>" +
									"<BaseCalculo>1203.20</BaseCalculo>" +
									"<Aliquota>0.05</Aliquota>" +
									"<ValorLiquidoNfse>1129.20</ValorLiquidoNfse>" +
									"<DescontoIncondicionado>0.00</DescontoIncondicionado>" +
									"<DescontoCondicionado>0.00</DescontoCondicionado>" +
									"</Valores>" +
								"<ItemListaServico>106 </ItemListaServico>" +
								"<CodigoCnae>0</CodigoCnae>" +
								"<CodigoTributacaoMunicipio>620400001</CodigoTributacaoMunicipio>" +
								"<Discriminacao>1 Consultoria Qlik. Referente 7:52 horas. 1.203,20. Valor Aprox dos Tributos: R$ 161,83 Federal, R$ 0,00 Estadual e R$ 54,63 Municipal Fonte: IBPT/FECOMERCIO PR ca7gi3</Discriminacao>" +
								"<CodigoMunicipio>4106902</CodigoMunicipio>" +
							"</Servico>" +
							"<ValorCredito>0</ValorCredito>" +
							"<PrestadorServico>" +
								"<IdentificacaoPrestador>" +
									"<Cnpj>04632074000100</Cnpj>" +
									"<InscricaoMunicipal>100904270306</InscricaoMunicipal>" +
								"</IdentificacaoPrestador>" +
								"<NomeFantasia>RGN REPRESENTAÇÃO COMERCIAL LTDA</NomeFantasia>" +
								"<Endereco>" +
									"<Endereco>ANITA GARIBALDI</Endereco>" +
									"<Numero>850 ES 210</Numero>" +
									"<Bairro>CABRAL</Bairro>" +
									"<CodigoMunicipio>4106902</CodigoMunicipio>" +
									"<Uf>PR</Uf>" +
									"<Cep>80540400</Cep>" +
								"</Endereco>" +
							"</PrestadorServico>" +
							"<TomadorServico>" +
								"<IdentificacaoTomador>" +
									"<CpfCnpj>" +
										"<Cnpj>77532521000114</Cnpj>" +
									"</CpfCnpj>" +
								"</IdentificacaoTomador>" +
								"<RazaoSocial>J.C.R. ADMINISTRACAO E PARTICIPACOES S/A</RazaoSocial>" +
								"<Endereco>" +
									"<Endereco>R FERNANDES DE BARROS</Endereco>" +
									"<Numero>514</Numero>" +
									"<Bairro>CRISTO REI</Bairro>" +
									"<CodigoMunicipio>4106902</CodigoMunicipio>" +
									"<Uf>PR</Uf>" +
									"<Cep>80045390</Cep>" +
								"</Endereco>" +
								"<Contato>" +
									"<Telefone>4133608400</Telefone>" +
									"<Email>CONTABILIDADE@GRUPOJCR.COM.BR</Email>" +
								"</Contato>" +
							"</TomadorServico>" +
						"</InfNfse>" +
					"</Nfse>";
			
//			xml = 
//					"<?xml version='1.0'?>" +
//							"<ArrayOfTcCompNfse xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>" +
//							  "<tcCompNfse>" +
//							    "<Nfse>" +
//							      "<InfNfse>" +
//							        "<Numero>92</Numero>" +
//							        "<CodigoVerificacao>O2DYA60Q</CodigoVerificacao>" +
//							        "<DataEmissao>2018-02-02T23:40:02.417</DataEmissao>" +
//							        "<DataEmissaoRps>0001-01-01T00:00:00</DataEmissaoRps>" +
//							        "<NaturezaOperacao>1</NaturezaOperacao>" +
//							        "<RegimeEspecialTributacao>0</RegimeEspecialTributacao>" +
//							        "<OptanteSimplesNacional>1</OptanteSimplesNacional>" +
//							        "<IncentivadorCultural>2</IncentivadorCultural>" +
//							        "<Competencia>0001-01-01T00:00:00</Competencia>" +
//							        "<NfseSubstituida>0</NfseSubstituida>" +
//							        "<Servico>" +
//							          "<Valores>" +
//							            "<ValorServicos>5258.40</ValorServicos>" +
//							            "<ValorDeducoes>0.00</ValorDeducoes>" +
//							            "<ValorPis>0.00</ValorPis>" +
//							            "<ValorCofins>0.00</ValorCofins>" +
//							            "<ValorInss>0.00</ValorInss>" +
//							            "<ValorIr>0.00</ValorIr>" +
//							            "<ValorCsll>0.00</ValorCsll>" +
//							            "<IssRetido>2</IssRetido>" +
//							            "<ValorIss>0.00</ValorIss>" +
//							            "<ValorIssRetido>0.00</ValorIssRetido>" +
//							            "<OutrasRetencoes>0.00</OutrasRetencoes>" +
//							            "<BaseCalculo>5258.40</BaseCalculo>" +
//							            "<Aliquota>0.00</Aliquota>" +
//							            "<ValorLiquidoNfse>5258.40</ValorLiquidoNfse>" +
//							            "<DescontoIncondicionado>0.00</DescontoIncondicionado>" +
//							            "<DescontoCondicionado>0.00</DescontoCondicionado>" +
//							          "</Valores>" +
//							          "<ItemListaServico>0107</ItemListaServico>" +
//							          "<CodigoCnae>0</CodigoCnae>" +
//							          "<Discriminacao>Prestação de serviços de informática referente ao mês de Janeiro/2018" +
//							"" +
//							"Depositar em conta corrente do Itaú" +
//							"AG: 9313" +
//							"C\\C: 38044-7" +
//							"Titular: Leonan Yglecias Mattos" +
//							"CPF: 070.769.649-61</Discriminacao>" +
//							          "<CodigoMunicipio>0</CodigoMunicipio>" +
//							        "</Servico>" +
//							        "<ValorCredito>0</ValorCredito>" +
//							        "<PrestadorServico>" +
//							          "<IdentificacaoPrestador>" +
//							            "<Cnpj>20732936000114</Cnpj>" +
//							            "<InscricaoMunicipal>06992020</InscricaoMunicipal>" +
//							          "</IdentificacaoPrestador>" +
//							          "<NomeFantasia>VALT &amp; MATTOS SOLUCOES EM TI LTDA - ME</NomeFantasia>" +
//							          "<Endereco>" +
//							            "<Endereco>R.ALBERTINA DE OLIVEIRA BARRETO</Endereco>" +
//							            "<Numero>29</Numero>" +
//							            "<Complemento />" +
//							            "<Bairro>CIDADE INDUSTRIAL</Bairro>" +
//							            "<CodigoMunicipio>4106902</CodigoMunicipio>" +
//							            "<Uf>PR</Uf>" +
//							            "<Cep>0</Cep>" +
//							          "</Endereco>" +
//							        "</PrestadorServico>" +
//							        "<TomadorServico>" +
//							          "<IdentificacaoTomador>" +
//							            "<CpfCnpj>" +
//							              "<Cnpj>77166098000186</Cnpj>" +
//							            "</CpfCnpj>" +
//							            "<InscricaoMunicipal>00867330</InscricaoMunicipal>" +
//							          "</IdentificacaoTomador>" +
//							          "<RazaoSocial>SIGMA DATASERV INFORMATICA S/A</RazaoSocial>" +
//							          "<Endereco>" +
//							            "<Endereco>TV.PINHEIRO</Endereco>" +
//							            "<Numero>230</Numero>" +
//							            "<Complemento />" +
//							            "<Bairro>REBOUÇAS</Bairro>" +
//							            "<CodigoMunicipio>4106902</CodigoMunicipio>" +
//							            "<Uf>PR</Uf>" +
//							            "<Cep>80230160</Cep>" +
//							          "</Endereco>" +
//							          "<Contato>" +
//							            "<Email>financeiro@sigma.com.br;leonanmattos@gmail.com</Email>" +
//							          "</Contato>" +
//							        "</TomadorServico>" +
//							      "</InfNfse>" +
//							    "</Nfse>" +
//							  "</tcCompNfse>" +
//							  "<tcCompNfse>" +
//							    "<Nfse>" +
//							      "<InfNfse>" +
//							        "<Numero>93</Numero>" +
//							        "<CodigoVerificacao>YT8FE204</CodigoVerificacao>" +
//							        "<DataEmissao>2018-02-04T20:16:47.653</DataEmissao>" +
//							        "<DataEmissaoRps>0001-01-01T00:00:00</DataEmissaoRps>" +
//							        "<NaturezaOperacao>1</NaturezaOperacao>" +
//							        "<RegimeEspecialTributacao>0</RegimeEspecialTributacao>" +
//							        "<OptanteSimplesNacional>1</OptanteSimplesNacional>" +
//							        "<IncentivadorCultural>2</IncentivadorCultural>" +
//							        "<Competencia>0001-01-01T00:00:00</Competencia>" +
//							        "<NfseSubstituida>0</NfseSubstituida>" +
//							        "<Servico>" +
//							          "<Valores>" +
//							            "<ValorServicos>3156.58</ValorServicos>" +
//							            "<ValorDeducoes>0.00</ValorDeducoes>" +
//							            "<ValorPis>0.00</ValorPis>" +
//							            "<ValorCofins>0.00</ValorCofins>" +
//							            "<ValorInss>0.00</ValorInss>" +
//							            "<ValorIr>0.00</ValorIr>" +
//							            "<ValorCsll>0.00</ValorCsll>" +
//							            "<IssRetido>2</IssRetido>" +
//							            "<ValorIss>0.00</ValorIss>" +
//							            "<ValorIssRetido>0.00</ValorIssRetido>" +
//							            "<OutrasRetencoes>0.00</OutrasRetencoes>" +
//							            "<BaseCalculo>3156.58</BaseCalculo>" +
//							            "<Aliquota>0.00</Aliquota>" +
//							            "<ValorLiquidoNfse>3156.58</ValorLiquidoNfse>" +
//							            "<DescontoIncondicionado>0.00</DescontoIncondicionado>" +
//							            "<DescontoCondicionado>0.00</DescontoCondicionado>" +
//							          "</Valores>" +
//							          "<ItemListaServico>0107</ItemListaServico>" +
//							          "<CodigoCnae>0</CodigoCnae>" +
//							          "<Discriminacao>Prestação de serviços em informática referente a Janeiro/2018" +
//							" " +
//							"Depositar em conta jurídica" +
//							"Banco: Itaú" +
//							"Agência: 4014" +
//							"C/C: 67780-3" +
//							"Titular: Leonan Yglecias Mattos" +
//							"CPF: 070.769.649-61</Discriminacao>" +
//							          "<CodigoMunicipio>0</CodigoMunicipio>" +
//							        "</Servico>" +
//							        "<ValorCredito>0</ValorCredito>" +
//							        "<PrestadorServico>" +
//							          "<IdentificacaoPrestador>" +
//							            "<Cnpj>20732936000114</Cnpj>" +
//							            "<InscricaoMunicipal>06992020</InscricaoMunicipal>" +
//							          "</IdentificacaoPrestador>" +
//							          "<NomeFantasia>VALT &amp; MATTOS SOLUCOES EM TI LTDA - ME</NomeFantasia>" +
//							          "<Endereco>" +
//							            "<Endereco>R.ALBERTINA DE OLIVEIRA BARRETO</Endereco>" +
//							            "<Numero>29</Numero>" +
//							            "<Complemento />" +
//							            "<Bairro>CIDADE INDUSTRIAL</Bairro>" +
//							            "<CodigoMunicipio>4106902</CodigoMunicipio>" +
//							            "<Uf>PR</Uf>" +
//							            "<Cep>0</Cep>" +
//							          "</Endereco>" +
//							        "</PrestadorServico>" +
//							        "<TomadorServico>" +
//							          "<IdentificacaoTomador>" +
//							            "<CpfCnpj>" +
//							              "<Cnpj>07978782000187</Cnpj>" +
//							            "</CpfCnpj>" +
//							            "<InscricaoMunicipal>05081545</InscricaoMunicipal>" +
//							          "</IdentificacaoTomador>" +
//							          "<RazaoSocial>EWAVE DO BRASIL INFORMATICA LTDA</RazaoSocial>" +
//							          "<Endereco>" +
//							            "<Endereco>R.EMILIANO PERNETA</Endereco>" +
//							            "<Numero>424</Numero>" +
//							            "<Complemento>CJ 131</Complemento>" +
//							            "<Bairro>CENTRO</Bairro>" +
//							            "<CodigoMunicipio>4106902</CodigoMunicipio>" +
//							            "<Uf>PR</Uf>" +
//							            "<Cep>80420080</Cep>" +
//							          "</Endereco>" +
//							          "<Contato>" +
//							            "<Email>nfe@ewave.com.br;leonanmattos@gmail.com</Email>" +
//							          "</Contato>" +
//							        "</TomadorServico>" +
//							      "</InfNfse>" +
//							    "</Nfse>" +
//							  "</tcCompNfse>" +
//							"</ArrayOfTcCompNfse>";
			
			JAXBContext context = JAXBContext.newInstance(NfseXML.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			NfseXML nfse = (NfseXML) unmarshaller.unmarshal(new StringReader(xml));
			System.out.println(nfse);
			
		} catch (PropertyException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return "/pages/nfse/incluir_nfse.xhtml";
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public List<String> getTeste() {
		return teste;
	}

	public void setTeste(List<String> teste) {
		this.teste = teste;
	}

	public List<String> getTesteSelecionados() {
		return testeSelecionados;
	}

	public void setTesteSelecionados(List<String> testeSelecionados) {
		this.testeSelecionados = testeSelecionados;
	}

}
