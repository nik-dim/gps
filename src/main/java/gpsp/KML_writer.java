package gpsp;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import de.micromata.opengis.kml.v_2_2_0.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.*;
import java.util.List;


public class KML_writer {
    public static final int NUMBER_OF_TAXIS = 11;
    static List<Color> colors = Arrays.asList(
            Color.black,
            Color.blue,
            Color.magenta,
            Color.orange,
            Color.red,
            Color.CYAN,
            Color.yellow,
            Color.darkGray,
            Color.GRAY,
            Color.blue,
            Color.PINK
    );


    public static void addTaxis(Document doc, ArrayList<Point> taxis){
        Style style = doc.createAndAddStyle().withId("taxi");
        IconStyle iconStyle = style.createAndSetIconStyle()
                .withColor("ff00d6ff")
                .withScale(1);
        Icon icon = iconStyle.createAndSetIcon()
                .withHref("http://www.gstatic.com/mapspro/images/stock/503-wht-blank_maps.png");
        for (int i=0 ; i<NUMBER_OF_TAXIS ; i++){
            Placemark p = doc.createAndAddPlacemark()
                    .withName("gpsp.Taxi " + Integer.toString(taxis.get(i).id) )
                    .withStyleUrl("#taxi");
            de.micromata.opengis.kml.v_2_2_0.Point point = p.createAndSetPoint()
                    .addToCoordinates(taxis.get(i).x, taxis.get(i).y);
        }
    }



//    public static void addClient(Document doc, gpsp.Client c){
//        Style style = doc.createAndAddStyle().withId("taxi");
//        IconStyle iconStyle = style.createAndSetIconStyle()
//                .withColor("ff00d6ff")
//                .withScale(1);
//        Icon icon = iconStyle.createAndSetIcon()
//                .withHref("http://www.gstatic.com/mapspro/images/stock/503-wht-blank_maps.png");
//    }



    public static void createKML(ArrayList<ArrayList<Point>> paths, ArrayList<Point> taxis, int id_bestPath, Client client) throws FileNotFoundException, JAXBException {
        Marshaller marshaller = JAXBContext.newInstance(new Class[]{Kml.class}).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper()
        {
            @Override
            public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
            {
                return namespaceUri.matches("http://www.w3.org/\\d{4}/Atom") ? "atom"
                        : (namespaceUri.matches("urn:oasis:names:tc:ciq:xsdschema:xAL:.*?") ? "xal"
                                : (namespaceUri.matches("http://www.google.com/kml/ext/.*?") ? "gx"
                                        : (namespaceUri.matches("http://www.opengis.net/kml/.*?") ? ""
                                                : (
                                                null
                                        )
                                )
                        )
                );
            }
        });

        Kml kml = new Kml();
        Document doc = kml.createAndSetDocument().withName("gpsp.Taxi Routes").withOpen(true);
        int i = 0;
        Style style = doc.createAndAddStyle();
        String hex = Integer.toHexString(Color.green.getRGB()).substring(2);
        style.withId("GREEN").createAndSetLineStyle().withColor(hex).withWidth(4.0);
        for (i=0 ; i<NUMBER_OF_TAXIS ; i++){
            style = doc.createAndAddStyle();
            String hex1 = Integer.toHexString(colors.get(i).getRGB()).substring(2);
            style.withId(Integer.toString(i)).createAndSetLineStyle().withColor(hex1).withWidth(4.0);
        }
        i = 0;
        for ( ArrayList<Point> path : paths) {
            Placemark p = doc.createAndAddPlacemark().withName("gpsp.Taxi " + Integer.toString(taxis.get(i).id));
            if (taxis.get(i).id == id_bestPath) {
                p.withStyleUrl("#GREEN").withDescription("This is the best path A* found to the client!!!");
            }
            else {
                p.withStyleUrl("#"+Integer.toString(i));
            }
            LineString lineString = p.createAndSetLineString().withExtrude(true).
                    withAltitudeMode(AltitudeMode.RELATIVE_TO_SEA_FLOOR);
            for (Point n : path) {
                lineString.addToCoordinates(n.getX(), n.getY());
            }
            i++;
        }

        addTaxis(doc, taxis);
        doc.createAndAddPlacemark()
                .withName("gpsp.Client").withOpen(Boolean.TRUE)
                .createAndSetPoint().addToCoordinates(client.getX(), client.getY());
        //marshals into file
        marshaller.marshal(kml, new File("C:\\Users\\nikdim\\IdeaProjects\\gps\\output6.kml"));
//        kml.marshal(new File("output5.kml"));
    }
}
