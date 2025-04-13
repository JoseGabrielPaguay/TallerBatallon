package co.edu.uniquindio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Batallon {
    private String nombre;
    private String id;

    private LinkedList<VehiculoApoyo> listVehiculosApoyo;
    private LinkedList<VehiculoBlindado> listVehiculosBlindados;
    private LinkedList<VehiculoTransporteTropa> listVehiculosTransporteTropa;
    private LinkedList<Vehiculo> listVehiculos;
    private LinkedList<Mision> listMisiones;
    private LinkedList<Soldado> listPersonal;

    public Batallon(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;

        this.listVehiculosApoyo = new LinkedList<>();
        this.listVehiculosBlindados = new LinkedList<>();
        this.listVehiculosTransporteTropa = new LinkedList<>();
        this.listMisiones = new LinkedList<>();
        this.listVehiculos = new LinkedList<>();
        this.listPersonal = new LinkedList<>();
    }

    public boolean registrarMision(LocalDate fechaMision, String ubicacionMision,
                                   LinkedList listPersonal, String idVehiculomision) {
        boolean flag = false;

        //Convertir de int a string
        String cantMisionesActuales = String.valueOf(listMisiones.size() + 1);

        Mision newMision = new Mision(cantMisionesActuales, fechaMision, ubicacionMision);

        for (Vehiculo vehiculo : listVehiculos) {
            if (vehiculo.getId().equals(idVehiculomision)) {
                newMision.setTheVehiculo(vehiculo);

                LinkedList<Mision> listTempo = vehiculo.getListMisiones();
                listTempo.add(newMision);

                vehiculo.setListMisiones(listTempo);
                listMisiones.add(newMision);
                flag = true;
                break;
            }
        }

        return flag;
    }


    public LinkedList<Vehiculo> obtenerVehiculosCantMisiones() {
        LinkedList<Vehiculo> vehiculosMisionesCompletadas = new LinkedList<>();


        for (VehiculoApoyo vehiculo : listVehiculosApoyo) {
            if (vehiculo.getMisionesCompletadas() > 50) {
                vehiculosMisionesCompletadas.add(vehiculo);
            }
        }

        for (VehiculoBlindado vehiculo : listVehiculosBlindados) {
            if (vehiculo.getMisionesCompletadas() > 50) {
                vehiculosMisionesCompletadas.add(vehiculo);
            }
        }

        for (VehiculoTransporteTropa vehiculo : listVehiculosTransporteTropa) {
            if (vehiculo.getMisionesCompletadas() > 50) {
                vehiculosMisionesCompletadas.add(vehiculo);
            }
        }

        return vehiculosMisionesCompletadas;
    }

    public String calcularKilometrajePromedioPorTipo() {
        double totalApoyo = 0;
        double totalBlindado = 0;
        double totalTransporte = 0;

        int cantApoyo = listVehiculosApoyo.size();
        int cantBlindado = listVehiculosBlindados.size();
        int cantTransporte = listVehiculosTransporteTropa.size();

        for (VehiculoApoyo v : listVehiculosApoyo) {
            totalApoyo += v.getKilometraje();
        }

        for (VehiculoBlindado v : listVehiculosBlindados) {
            totalBlindado += v.getKilometraje();
        }

        for (VehiculoTransporteTropa v : listVehiculosTransporteTropa) {
            totalTransporte += v.getKilometraje();
        }

        double promApoyo = (cantApoyo == 0) ? 0 : totalApoyo / cantApoyo;
        double promBlindado = (cantBlindado == 0) ? 0 : totalBlindado / cantBlindado;
        double promTransporte = (cantTransporte == 0) ? 0 : totalTransporte / cantTransporte;

        return "Kilometraje promedio:\n" +
                "- Vehículos de apoyo: " + promApoyo + " km\n" +
                "- Vehículos blindados: " + promBlindado + " km\n" +
                "- Vehículos de transporte de tropas: " + promTransporte + " km";
    }

    public LinkedList<Mision> obtenerMisionesPorUbicacionYFechas(String ubicacion, LocalDate fechaInicio, LocalDate fechaFin) {
        LinkedList<Mision> misionUbyFe = new LinkedList<>();

        for (Mision mision : listMisiones) {
            if (mision.getUbicacion().equalsIgnoreCase(ubicacion)) {
                LocalDate fecha = mision.getFecha();
                if ((fecha.isEqual(fechaInicio) || fecha.isAfter(fechaInicio)) &&
                        (fecha.isEqual(fechaFin) || fecha.isBefore(fechaFin))) {
                    misionUbyFe.add(mision);
                }
            }
        }
        return misionUbyFe;
    }


    public Vehiculo obtenerVehiculoConMasMisiones() {
        Vehiculo vehiculoMax = null;
        int maxMisiones = -1;
        for (VehiculoApoyo v : listVehiculosApoyo) {
            if (v.getMisionesCompletadas() > maxMisiones) {
                maxMisiones = v.getMisionesCompletadas();
                vehiculoMax = v;
            }
        }
        for (VehiculoBlindado v : listVehiculosBlindados) {
            if (v.getMisionesCompletadas() > maxMisiones) {
                maxMisiones = v.getMisionesCompletadas();
                vehiculoMax = v;
            }
        }
        for (VehiculoTransporteTropa v : listVehiculosTransporteTropa) {
            if (v.getMisionesCompletadas() > maxMisiones) {
                maxMisiones = v.getMisionesCompletadas();
                vehiculoMax = v;
            }
        }
        return vehiculoMax;
    }

    public boolean agregarVehiculoApoyo(VehiculoApoyo vehiculo) {
        listVehiculosApoyo.add(vehiculo);
        listVehiculos.add(vehiculo);
        return true;
    }

    public VehiculoApoyo buscarVehiculoApoyoPorId(String id) {
        for (VehiculoApoyo v : listVehiculosApoyo) {
            if (v.getId().equalsIgnoreCase(id)) return v;
        }
        return null;
    }

    public boolean actualizarVehiculoApoyo(String id, VehiculoApoyo nuevoVehiculo) {
        VehiculoApoyo actual = buscarVehiculoApoyoPorId(id);
        if (actual != null) {
            listVehiculosApoyo.remove(actual);
            listVehiculos.remove(actual);
            return agregarVehiculoApoyo(nuevoVehiculo);
        }
        return false;
    }

    public boolean eliminarVehiculoApoyo(String id) {
        VehiculoApoyo v = buscarVehiculoApoyoPorId(id);
        if (v != null) {
            listVehiculosApoyo.remove(v);
            listVehiculos.remove(v);
            return true;
        }
        return false;
    }

    public boolean agregarVehiculoBlindado(VehiculoBlindado vehiculo) {
        listVehiculosBlindados.add(vehiculo);
        listVehiculos.add(vehiculo);
        return true;
    }

    public VehiculoBlindado buscarVehiculoBlindadoPorId(String id) {
        for (VehiculoBlindado v : listVehiculosBlindados) {
            if (v.getId().equalsIgnoreCase(id)) return v;
        }
        return null;
    }

    public boolean actualizarVehiculoBlindado(String id, VehiculoBlindado nuevoVehiculo) {
        VehiculoBlindado actual = buscarVehiculoBlindadoPorId(id);
        if (actual != null) {
            listVehiculosBlindados.remove(actual);
            listVehiculos.remove(actual);
            return agregarVehiculoBlindado(nuevoVehiculo);
        }
        return false;
    }

    public boolean eliminarVehiculoBlindado(String id) {
        VehiculoBlindado v = buscarVehiculoBlindadoPorId(id);
        if (v != null) {
            listVehiculosBlindados.remove(v);
            listVehiculos.remove(v);
            return true;
        }
        return false;
    }

    public boolean agregarVehiculoTransporteTropa(VehiculoTransporteTropa vehiculo) {
        listVehiculosTransporteTropa.add(vehiculo);
        listVehiculos.add(vehiculo);
        return true;
    }

    public VehiculoTransporteTropa buscarVehiculoTransporteTropaPorId(String id) {
        for (VehiculoTransporteTropa v : listVehiculosTransporteTropa) {
            if (v.getId().equalsIgnoreCase(id)) return v;
        }
        return null;
    }

    public boolean actualizarVehiculoTransporteTropa(String id, VehiculoTransporteTropa nuevoVehiculo) {
        VehiculoTransporteTropa actual = buscarVehiculoTransporteTropaPorId(id);
        if (actual != null) {
            listVehiculosTransporteTropa.remove(actual);
            listVehiculos.remove(actual);
            return agregarVehiculoTransporteTropa(nuevoVehiculo);
        }
        return false;
    }

    public boolean eliminarVehiculoTransporteTropa(String id) {
        VehiculoTransporteTropa v = buscarVehiculoTransporteTropaPorId(id);
        if (v != null) {
            listVehiculosTransporteTropa.remove(v);
            listVehiculos.remove(v);
            return true;
        }
        return false;
    }

    public boolean agregarMision(Mision mision) {
        return listMisiones.add(mision);
    }

    public Mision buscarMisionPorId(String id) {
        for (Mision m : listMisiones) {
            if (m.getId().equalsIgnoreCase(id)) return m;
        }
        return null;
    }

    public boolean actualizarMision(String id, Mision nuevaMision) {
        Mision actual = buscarMisionPorId(id);
        if (actual != null) {
            listMisiones.remove(actual);
            return listMisiones.add(nuevaMision);
        }
        return false;
    }

    public boolean eliminarMision(String id) {
        Mision m = buscarMisionPorId(id);
        if (m != null) {
            listMisiones.remove(m);
            return true;
        }
        return false;
    }

    public boolean agregarSoldado(Soldado soldado) {
        return listPersonal.add(soldado);
    }


    public boolean actualizarSoldado(String id, Soldado nuevoSoldado) {
        Soldado actual = buscarSoldadoId(id);
        if (actual != null) {
            listPersonal.remove(actual);
            return listPersonal.add(nuevoSoldado);
        }
        return false;
    }

    public boolean eliminarSoldado(String id) {
        Soldado s = buscarSoldadoId(id);
        if (s != null) {
            listPersonal.remove(s);
            return true;
        }
        return false;
    }

    public boolean asignarSoldadoAMision(Soldado soldado, Mision mision) {
        if (soldado.estaDisponible()) {
            mision.getListPersonal().add(soldado);
            soldado.setDisponible(0.0);
            return true;
        }
        return false;
    }

    public void liberarSoldadosDeMision(Mision mision) {
        for (Soldado s : mision.getListPersonal()) {
            s.setDisponible(1.0);
        }
    }

    public List<Soldado> buscarSoldadosPorEspecialidad(TipoFuncion tipoEspecialidad) {
        List<Soldado> resultado = new ArrayList<>();
        for (Soldado s : listPersonal) {
            if (s.getFuncionSoldado() == tipoEspecialidad) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    public List<Soldado> obtenerSoldadosDisponiblesPorRango(RangoMilitar rango) {
        List<Soldado> resultado = new ArrayList<>();
        for (Soldado s : listPersonal) {
            if (s.getRangoMilitar() == rango && s.estaDisponible()) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    public double calcularEdadPromedio() {
        if (listPersonal.isEmpty()) {
            return 0.0;
        }
        int suma = 0;
        for (Soldado s : listPersonal) {
            suma += s.getEdad();
        }
        return (double) suma / listPersonal.size();
    }

    public Soldado buscarSoldadoId(String id) {
        for (Soldado soldado : listPersonal) {
            if (soldado.getId().equalsIgnoreCase(id)) return soldado;
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<VehiculoApoyo> getListVehiculosApoyo() {
        return listVehiculosApoyo;
    }

    public void setListVehiculosApoyo(LinkedList<VehiculoApoyo> listVehiculosApoyo) {
        this.listVehiculosApoyo = listVehiculosApoyo;
    }

    public LinkedList<VehiculoBlindado> getListVehiculosBlindados() {
        return listVehiculosBlindados;
    }

    public void setListVehiculosBlindados(LinkedList<VehiculoBlindado> listVehiculosBlindados) {
        this.listVehiculosBlindados = listVehiculosBlindados;
    }

    public LinkedList<VehiculoTransporteTropa> getListVehiculosTransporteTropa() {
        return listVehiculosTransporteTropa;
    }

    public void setListVehiculosTransporteTropa(LinkedList<VehiculoTransporteTropa> listVehiculosTransporteTropa) {
        this.listVehiculosTransporteTropa = listVehiculosTransporteTropa;
    }

    public LinkedList<Mision> getListMisiones() {
        return listMisiones;
    }

    public void setListMisiones(LinkedList<Mision> listMisiones) {
        this.listMisiones = listMisiones;
    }

    public LinkedList<Vehiculo> getListVehiculos() {
        return listVehiculos;
    }

    public void setListVehiculos(LinkedList<Vehiculo> listVehiculos) {
        this.listVehiculos = listVehiculos;
    }

    public LinkedList<Soldado> getListPersonal() {
        return listPersonal;
    }

    public void setListPersonal(LinkedList<Soldado> listPersonal) {
        this.listPersonal = listPersonal;
    }
}