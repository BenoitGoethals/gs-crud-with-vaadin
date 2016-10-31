package hello;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

	private final CustomerRepository repo;

	private final CustomerEditor editor;

	final Grid grid;

	final TextField filter;

	private final Button addNewBtn;
	// Add the next two lines:
	private CustomerService service = CustomerService.getInstance();
	private Grid grid2 = new Grid();

	@Autowired
	public VaadinUI(CustomerRepository repo, CustomerEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid();
		this.filter = new TextField();
		this.addNewBtn = new Button("New customer", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		TextField name = new TextField("Your Name", "Vaadin");

// Access any server-side API directly.
		//String serverName = servletContext.getServerInfo();

// Create a Button and define a server-side click listener.
		Button greetButton = new Button("Greet the Server");
		greetButton.addClickListener(event ->
				Notification.show("Hello " + name.getValue() + "!\n" +
						"I'm " + "d"));
		grid2.setColumns("firstName", "lastName", "email");
		List<Customer> customers = service.findAll();
		grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn,greetButton,name);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor,grid2);
		setContent(mainLayout);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("id", "firstName", "lastName");

		filter.setInputPrompt("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listCustomers(e.getText()));

		// Connect selected Customer to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editor.setVisible(false);
			}
			else {
				editor.editCustomer((Customer) grid.getSelectedRow());
			}
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
		updateList();
	}

	// tag::listCustomers[]
	void listCustomers(String text) {
		if (StringUtils.isEmpty(text)) {
			grid.setContainerDataSource(
					new BeanItemContainer(Customer.class, repo.findAll()));
		}
		else {
			grid.setContainerDataSource(new BeanItemContainer(Customer.class,
					repo.findByLastNameStartsWithIgnoreCase(text)));
		}
	}
	// end::listCustomers[]
	public void updateList() {
		// fetch list of Customers from service and assign it to Grid
		List<Customer> customers = service.findAll();
		grid2.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
	}

}
