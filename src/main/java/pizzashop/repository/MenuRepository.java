package pizzashop.repository;

import pizzashop.model.MenuDataModel;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuRepository {
    private static String filename = "/data/menu.txt";
    private List<MenuDataModel> listMenu;
    private static final Logger logger = Logger.getAnonymousLogger();

    private void readMenu() {

        try (BufferedReader br = new BufferedReader(new FileReader(new File(PaymentRepository.class.getResource(filename).toURI())))) {
            this.listMenu = new ArrayList();
            String line = null;
            while ((line = br.readLine()) != null) {
                MenuDataModel menuItem = getMenuItem(line);
                listMenu.add(menuItem);
            }
        } catch (URISyntaxException | IOException e) {
            logger.log(Level.WARNING, e.getMessage());

        }
    }

    private MenuDataModel getMenuItem(String line) {
        MenuDataModel item = null;
        if (line == null || line.equals("")) return null;
        StringTokenizer st = new StringTokenizer(line, ",");
        String name = st.nextToken();
        double price = Double.parseDouble(st.nextToken());
        item = new MenuDataModel(name, 0, price);
        return item;
    }

    public List<MenuDataModel> getMenu() {
        readMenu();//create a new menu for each table, on request
        return listMenu;
    }

}
