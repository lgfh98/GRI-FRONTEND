package co.edu.uniquindio.gri;

import org.junit.*;

import java.math.BigInteger;
import java.util.List;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;

import co.edu.uniquindio.gri.dao.CentroDAO;
import co.edu.uniquindio.gri.dao.FacultadDAO;
import co.edu.uniquindio.gri.dao.GrupoDAO;
import co.edu.uniquindio.gri.dao.InvestigadorDAO;
import co.edu.uniquindio.gri.dao.LineasInvestigacionDAO;
import co.edu.uniquindio.gri.dao.ProduccionDAO;
import co.edu.uniquindio.gri.dao.ProgramaDAO;
import co.edu.uniquindio.gri.webcontroller.WebController;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartWebApplication.class)
public class GRITest {
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	GrupoDAO grupoDAO;

	@Autowired
	InvestigadorDAO investigadorDAO;

	@Autowired
	CentroDAO centroDAO;

	@Autowired
	ProgramaDAO programaDAO;

	@Autowired
	FacultadDAO facultadDAO;

	@Autowired
	ProduccionDAO produccionDAO;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	LineasInvestigacionDAO lineasInvestigacionDAO;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testHelloEndpointIsOK() throws Exception {
		this.mockMvc.perform(get("/login")).andExpect(status().isOk());
		this.mockMvc.perform(get("/inicio")).andExpect(status().isOk());
		this.mockMvc.perform(get("/investigadores")).andExpect(status().isOk());
		this.mockMvc.perform(get("/grupos")).andExpect(status().isOk());
		this.mockMvc.perform(get("/centros")).andExpect(status().isOk());
	}

	@Test
	public void getInvestigadorestest() throws Exception {
		assertEquals(1, investigadorDAO.getInvestigadoresEmeritosFacultad(Long.parseLong("4")).size());
	}

	@Test
	@GetMapping(value = { "/", "inicio" })
	public void mainTest() throws Exception {
		this.mockMvc.perform(get("/login")).andExpect(status().isOk())
				.andExpect(model().attribute("cantFacultades", equalTo(new BigInteger("7"))));
	}
}
