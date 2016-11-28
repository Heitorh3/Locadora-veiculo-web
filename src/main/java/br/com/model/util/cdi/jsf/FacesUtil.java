package br.com.model.util.cdi.jsf;

import java.util.Arrays;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

public class FacesUtil {

	public static void addSuccessMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO,message, message)); 
	}
	
	public static void addErrorMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR,message, message)); 
	}
	
	public static void atualizaTela(String componentes[]){
		RequestContext.getCurrentInstance().update(Arrays.asList(componentes));
	}
	
	public static String getLoginParam(FacesContext fc, String paramName){
		
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get(paramName);
		
	}
}