package pl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

import dl.Publicacion;
import bl.Ejb;

@ManagedBean
@SessionScoped
public class PublicacionBean {

	@EJB
	Ejb ejb = new Ejb();

	private Publicacion publicacion = new Publicacion();
	private Part image;
	boolean added;
	boolean mm;

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	public boolean isMm() {
		return mm;
	}

	public void setMm(boolean mm) {
		this.mm = mm;
	}

	public String addPublicacion() {
		if (mm) {
			byte[] buffer = new byte[4096000];
			try {
				InputStream in = image.getInputStream();
				OutputStream out = new OutputStream() {

					@Override
					public void write(int b) throws IOException {
						// TODO Auto-generated method stub
					}
				};
				
				int lenght;
				while ((lenght = in.read(buffer)) > 0) {
					out.write(buffer, 0, lenght);
				}
				publicacion.setMultimedia(buffer);
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		added = false;
		added = ejb.addPublicacion(publicacion);
		publicacion = new Publicacion();
		return "listaPublicaciones.xhtml";
	}

	public Part getImage() {
		return image;
	}

	public void setImage(Part image) {
		this.image = image;
	}

	public boolean isAdded() {
		return added;
	}

	public void setAdded(boolean added) {
		this.added = added;
	}

	public String editarPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
		return "editarPublicacion.xhtml";
	}

	public String editPublicacion() {

		ejb.editarPublicacion(publicacion);
		return "listaPublicaciones.xhtml";
	}

	public String entrarPublicacion(int idPublicacion) {
		this.publicacion = ejb.getPublicacion(idPublicacion);
		ejb.incrementarVisualizacion(idPublicacion);
		return "publicacion.xhtml";
	}
	
	public String subirPublicacion(){
		this.publicacion=ejb.inicializarPublicacion();
		return "subirpublicacion.xhtml";
	}
	
	public String getFotoLike(int idPublicacion)
	{
		String fotoLike="fotos/like1.png";
		if(ejb.getLiked(idPublicacion))
			fotoLike="fotos/like.png";
		return fotoLike;
	}


}
