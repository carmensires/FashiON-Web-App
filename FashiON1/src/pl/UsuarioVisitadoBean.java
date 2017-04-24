package pl;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import dl.Publicacion;
import dl.Usuario;
import bl.Ejb;


@ManagedBean
@RequestScoped
public class UsuarioVisitadoBean {
	
	@EJB
	Ejb ejb=new Ejb();
	
	private Usuario usuario = new Usuario();


	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	public String getNSeguidores()
	{
		String nSeguidores;
		int tam=this.getSeguidores().size();
		nSeguidores=tam+" seguidores";
		return nSeguidores;
	}
	
	public String getNSeguidos()
	{
		String nSeguidos;
		int tam=this.getSeguidos().size();
		nSeguidos=tam+" seguidos";
		return nSeguidos;
	}
	
	public String getNPosts()
	{
		String nPosts;
		int tam=this.getPublicacionesUsuario().size();
		nPosts=tam+"posts";
		return nPosts;
	}
	
	public List<Usuario> getSeguidores()
	{
		List<Usuario> seguidores=ejb.getSeguidores(usuario.getIdUser());
		return seguidores;
	}
	
	public List<Usuario> getSeguidos()
	{
		List<Usuario> seguidos=ejb.getSeguidos(usuario.getIdUser());
		return seguidos;
	}
	
	public List<Publicacion> getPublicacionesUsuario()
	{
		List<Publicacion> publicacionesUsuario=ejb.getPublicacionesUsuario(usuario.getIdUser());
		return publicacionesUsuario;
	}
	
	public String entrarPerfil(Usuario user){
		String link;
		if(user.getIdUser()==ejb.getUsuario().getIdUser())
			link="perfil.xhtml";
		else
		{
			usuario=user;
			link="perfilVisitado.xhtml";
		}
		return link;
		
	}
	
	public void seguirUsuario(int idUser){
		
		ejb.addAmigo(idUser);
		ejb.addNotificacionSeguir(idUser);
	}
	
	public void dejarDeSeguirUsuario(int idUser){
		ejb.removeAmigo(idUser);
		ejb.removeNotificacionSeguir(idUser);
	}
	
	public boolean usuarioNoSeguido(){
		return ejb.comprobarSeguido(usuario.getIdUser());
	}
	
	public boolean usuarioSeguido(){
		boolean comprobar=true;
		if(ejb.comprobarSeguido(usuario.getIdUser())==true)
			comprobar=false;
		return comprobar;
		
	}
	
}