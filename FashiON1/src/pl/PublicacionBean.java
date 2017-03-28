package pl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;

import dl.Publicacion;
import bl.Ejb;


@ManagedBean
@RequestScoped
public class PublicacionBean {
	
	@EJB
	Ejb ejb=new Ejb();
	
	private Publicacion publicacion=new Publicacion();
	public Part getImage() {
		return image;
	}

	public void setImage(Part image) {
		this.image = image;
	}
	private Part image;
	boolean added;
	

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}
	
	public void addPublicacion()
	{
		added=false;
		try {
			InputStream in=image.getInputStream();
			OutputStream out=new OutputStream() {
				
				@Override
				public void write(int b) throws IOException {
					// TODO Auto-generated method stub
					
				}
			};
			byte[] buffer= new byte[4096];
			int lenght;
			while((lenght=in.read(buffer))>0)
			{
				out.write(buffer, 0, lenght);
			}
			publicacion.setMultimedia(buffer);
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		added=ejb.addPublicacion(publicacion);
	}

	public boolean isAdded() {
		return added;
	}

	public void setAdded(boolean added) {
		this.added = added;
	}
	

	
	
	

}
