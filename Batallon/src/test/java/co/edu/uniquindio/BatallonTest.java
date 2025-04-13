package co.edu.uniquindio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
class BatallonTest {
    /**
     * CLase para probar agregar una nueva mision, kilometraje de vehiculos, obtener misiones por ubi y fecha, y obtener vehiculos con mas misiones
     *
     * @author Jose
     * @since 2025-04
     * Licencia GNU/GPL V3.0
     */
    private static final Logger LOG = Logger.getLogger(BatallonTest.class.getName());

    @Test
    @DisplayName("prueba Funcionalidades test")
    public void testUno() {
        LOG.info("inicio del metodo testUoo");
        //comprueba que dos elementos sean iguales
        assertEquals(4, 4);
        //comprueba que dos elementos no sean iguales
        assertNotEquals(1, "1");
        //comprueba que un elemento sea verdadedo
        assertTrue(1 > 0);
        //comprueba que un elemento sea falso
        assertFalse(1 < 0);
        //
        assertNull(null);
        LOG.info("Fin del metodo tstUno");
    }

    @Test
    @DisplayName("Prueba metodo para registrar una mision")
    public void testRegistrarMision() {
        LOG.info("inicio del metodo testRegistrarMision");
        Batallon batallon = new Batallon("Batallon 1", "B001");

        VehiculoApoyo vehiculo = new VehiculoApoyo("V001", "Modelo X", 2020, 1000.0, EstadoOperativo.DISPONIBLE, TipoFuncion.LOGISTICA);
        batallon.getListVehiculos().add(vehiculo);

        LinkedList<String> personal = new LinkedList<>();
        personal.add("Soldado 1");

        boolean resultado = batallon.registrarMision(LocalDate.now(), "Base Norte", personal, "V001");

        assertTrue(resultado);
        LOG.info("Fin del metodo testRegistrarMision");
    }

    @Test
    @DisplayName("Prueba metodo test kilometraje promedio por tipo de vehiculo")
    public void testKilometrajePromedioPorTipo() {
        LOG.info("inicio del metodo testKilometrajePromedioPorTipo");
        Batallon batallon = new Batallon("Batallon X", "BX01");

        batallon.getListVehiculosApoyo().add(new VehiculoApoyo("A1", "ApoyoX", 2018, 1000, EstadoOperativo.DISPONIBLE, TipoFuncion.LOGISTICA));
        batallon.getListVehiculosBlindados().add(new VehiculoBlindado("B1", "BlindadoX", 2017, 2000, EstadoOperativo.DISPONIBLE, 3));
        batallon.getListVehiculosTransporteTropa().add(new VehiculoTransporteTropa("T1", "TransporteX", 2019, 3000, EstadoOperativo.DISPONIBLE, 10));

        String resultado = batallon.calcularKilometrajePromedioPorTipo();

        assertTrue(resultado.contains("1000.0"));
        assertTrue(resultado.contains("2000.0"));
        assertTrue(resultado.contains("3000.0"));
        LOG.info("Fin del metodo testKilometrajePromedioPorTipo");
    }

    @Test
    @DisplayName("Prueba metodo obtener vehiculos por ubicacion y fecha")
    public void testObtenerMisionesPorUbicacionYFechas() {
        LOG.info("inicio del metodo testObtenerMisionesPorUbicacionYFechas");
        Batallon batallon = new Batallon("Batallon Z", "BZ01");

        Mision m1 = new Mision("1", LocalDate.of(2023, 1, 10), "Base Alfa");
        Mision m2 = new Mision("2", LocalDate.of(2023, 1, 15), "Base Alfa");
        Mision m3 = new Mision("3", LocalDate.of(2023, 2, 1), "Base Beta");

        batallon.getListMisiones().add(m1);
        batallon.getListMisiones().add(m2);
        batallon.getListMisiones().add(m3);

        LinkedList<Mision> resultado = batallon.obtenerMisionesPorUbicacionYFechas(
                "Base Alfa",
                LocalDate.of(2023, 1, 9),
                LocalDate.of(2023, 1, 20)
        );

        assertEquals(2, resultado.size());
        LOG.info("Fin del metodo testObtenerMisionesPorUbicacionYFechas");
    }

    @Test
    @DisplayName("prueba del metodo obtener vehiculos con mas misiones")
    public void testObtenerVehiculoConMasMisiones() {
        LOG.info("inicio del metodo testObtenerVehiculoConMasMisiones");
        Batallon batallon = new Batallon("Batallon Bravo", "BB01");

        VehiculoApoyo v1 = new VehiculoApoyo("V1", "ModeloA", 2020, 500, EstadoOperativo.DISPONIBLE, TipoFuncion.MEDICO);
        v1.setMisionesCompletadas(5);

        VehiculoBlindado v2 = new VehiculoBlindado("V2", "ModeloB", 2019, 800, EstadoOperativo.DISPONIBLE, 2);
        v2.setMisionesCompletadas(8);

        VehiculoTransporteTropa v3 = new VehiculoTransporteTropa("V3", "ModeloC", 2021, 1000, EstadoOperativo.DISPONIBLE, 15);
        v3.setMisionesCompletadas(3);

        batallon.getListVehiculosApoyo().add(v1);
        batallon.getListVehiculosBlindados().add(v2);
        batallon.getListVehiculosTransporteTropa().add(v3);

        Vehiculo resultado = batallon.obtenerVehiculoConMasMisiones();

        assertEquals("V2", resultado.getId());
        LOG.info("Fin del metodo testObtenerVehiculoConMasMisiones");
    }

    //Test parte 2-Soldado
    @Test
    @DisplayName("prueba del metodo asignar soldado a mision")
    public void testAsignarSoldadoAMision() {
        LOG.info("inicio del metodo testAsignarSoldadoAMision");
        Soldado soldado = new Soldado("S1", "Carlos", 30, 1.0, TipoFuncion.MEDICO, RangoMilitar.CABO);
        Mision mision = new Mision("M1", LocalDate.now(), "Base Norte");
        mision.setListPersonal(new LinkedList<>());
        Batallon batallon = new Batallon("Alfa", "A1");
        boolean asignado = batallon.asignarSoldadoAMision(soldado, mision);

        assertTrue(asignado);
        assertEquals(0.0, soldado.getDisponible(), 0.0);
        assertTrue(mision.getListPersonal().contains(soldado));
        LOG.info("Fin del metodo testAsignarSoldadoAMision");
    }

    @Test
    @DisplayName("prueba del metodo liberar soldado de mision")
    public void testLiberarSoldadosDeMision() {
        LOG.info("inicio del metodo testLiberarSoldadosDeMision");
        Soldado s1 = new Soldado("S1", "Ana", 25, 0.0, TipoFuncion.LOGISTICA, RangoMilitar.CABO);
        Soldado s2 = new Soldado("S2", "Luis", 28, 0.0, TipoFuncion.LOGISTICA, RangoMilitar.SARGENTO);
        Mision mision = new Mision("M1", LocalDate.now(), "Zona A");
        LinkedList<Soldado> personal = new LinkedList<>();
        personal.add(s1);
        personal.add(s2);
        mision.setListPersonal(personal);
        Batallon batallon = new Batallon("Bravo", "B1");
        batallon.liberarSoldadosDeMision(mision);

        assertEquals(1.0, s1.getDisponible(), 0.0);
        assertEquals(1.0, s2.getDisponible(), 0.0);
        LOG.info("Fin del metodo testLiberarSoldadosDeMision");
    }

    @Test
    @DisplayName("prueba del metodo buscar soldados por especialidad")
    public void testBuscarSoldadosPorEspecialidad() {
        LOG.info("inicio del metodo testBuscarSoldadosPorEspecialidad");
        Batallon batallon = new Batallon("Alfa", "A1");
        batallon.agregarSoldado(new Soldado("S1", "Juan", 25, 1.0, TipoFuncion.LOGISTICA, RangoMilitar.SOLDADO));
        batallon.agregarSoldado(new Soldado("S2", "Carlos", 30, 1.0, TipoFuncion.COMUNICACIONES, RangoMilitar.CABO));
        batallon.agregarSoldado(new Soldado("S3", "Ana", 28, 1.0, TipoFuncion.LOGISTICA, RangoMilitar.SARGENTO));

        List<Soldado> soldadosLogistica = batallon.buscarSoldadosPorEspecialidad(TipoFuncion.LOGISTICA);

        assertEquals(2, soldadosLogistica.size());
        LOG.info("Fin del metodo testBuscarSoldadosPorEspecialidad");
    }

    @Test
    @DisplayName("prueba del metodo buscar soldados por rango")
    public void testObtenerSoldadosDisponiblesPorRango() {
        LOG.info("inicio del metodo testObtenerSoldadosDisponiblesPorRango");
        Batallon batallon = new Batallon("Alfa", "A1");
        batallon.agregarSoldado(new Soldado("S1", "Juan", 25, 1.0, TipoFuncion.LOGISTICA, RangoMilitar.SOLDADO));
        batallon.agregarSoldado(new Soldado("S2", "Carlos", 30, 1.0, TipoFuncion.COMUNICACIONES, RangoMilitar.CABO));
        batallon.agregarSoldado(new Soldado("S3", "Ana", 28, 1.0, TipoFuncion.LOGISTICA, RangoMilitar.SARGENTO));
        batallon.agregarSoldado(new Soldado("S4", "Luis", 32, 0.0, TipoFuncion.COMUNICACIONES, RangoMilitar.SOLDADO));

        List<Soldado> soldadosDisponibles = batallon.obtenerSoldadosDisponiblesPorRango(RangoMilitar.SOLDADO);

        assertEquals(1, soldadosDisponibles.size());
        assertEquals("S1", soldadosDisponibles.get(0).getId());
        LOG.info("Fin del metodo testObtenerSoldadosDisponiblesPorRango");
    }

    @Test
    @DisplayName("prueba del metodo calcular edad promedio del personal")
    public void testCalcularEdadPromedio() {
        LOG.info("inicio del metodo testCalcularEdadPromedio");
        Batallon batallon = new Batallon("Alfa", "A1");
        batallon.agregarSoldado(new Soldado("S1", "Juan", 25, 1.0, TipoFuncion.LOGISTICA, RangoMilitar.SOLDADO));
        batallon.agregarSoldado(new Soldado("S2", "Carlos", 30, 1.0, TipoFuncion.COMUNICACIONES, RangoMilitar.CABO));
        batallon.agregarSoldado(new Soldado("S3", "Ana", 20, 1.0, TipoFuncion.LOGISTICA, RangoMilitar.SARGENTO));

        double edadPromedio = batallon.calcularEdadPromedio();

        assertEquals(25, edadPromedio);
        LOG.info("Fin del metodo testCalcularEdadPromedio");
    }

    @Test
    @DisplayName("prueba del metodo buscar soldado por id")
    public void testBuscarSoldadoId() {
        LOG.info("inicio del metodo testBuscarSoldadoId");
        Batallon batallon = new Batallon("Alfa", "A1");
        batallon.agregarSoldado(new Soldado("S1", "Juan", 25, 1.0, TipoFuncion.LOGISTICA, RangoMilitar.SOLDADO));
        batallon.agregarSoldado(new Soldado("S2", "Carlos", 30, 1.0, TipoFuncion.COMUNICACIONES, RangoMilitar.CABO));

        Soldado soldadoEncontrado = batallon.buscarSoldadoId("S1");

        assertNotNull(soldadoEncontrado);
        assertEquals("Juan", soldadoEncontrado.getNombre());
        Soldado soldadoNoEncontrado = batallon.buscarSoldadoId("S3");
        assertNull(soldadoNoEncontrado);
        LOG.info("Fin del metodo testBuscarSoldadoId");
    }
}