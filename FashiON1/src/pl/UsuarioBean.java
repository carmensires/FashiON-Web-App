package pl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dl.Usuario;
import bl.Ejb;


@ManagedBean
@SessionScoped
public class UsuarioBean {
	
	@EJB
	Ejb ejb=new Ejb();
	
	private Usuario usuario = new Usuario();
	private int passwordOk=3;
	private boolean passOk;
	private boolean comprobado;

	

	public int getPasswordOk() {
		return passwordOk;
	}

	public void setPasswordOk(int passwordOk) {
		this.passwordOk = passwordOk;
	}

	public boolean isPassOk() {
		return passOk;
	}

	public void setPassOk(boolean passOk) {
		this.passOk = passOk;
	}

	public boolean isComprobado() {
		return comprobado;
	}

	public void setComprobado(boolean comprobado) {
		this.comprobado = comprobado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void comprobarUsuario()
	{
		passwordOk=ejb.comprobarUsuario(usuario.getUserName(), usuario.getPassword());
	}
	
	public String getOkText()
	{
		String okText;
		if(passwordOk==0)
			okText="Contraseña correcta";
		else if(passwordOk==1)
			okText="No existe el usuario introducido";
		else if(passwordOk==2)
			okText="Contraseña incorrecta";
		else
			okText="ERROR.";
		return okText;
	}

}
