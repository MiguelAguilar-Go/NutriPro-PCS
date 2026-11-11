/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nutricionalappfx.dominio;

import static java.awt.SystemColor.info;
import nutricionalappfx.dominio.contrato.IAutenticacionRepository;
import nutricionalappfx.dominio.pojo.Autenticacion;
import nutricionalappfx.dominio.pojo.Colaborador;
import nutricionalappfx.dominio.pojo.ColaboradorEstatus;
import utilidad.Utilidades;

/**
 *
 * @author Miguel Aguilar
 */
public class AutenticacionDM {
    
    private final IAutenticacionRepository contrato; 
    public final int CREDENCIALES_INCORRECTAS = -1;
    public final int ESTATUS_COLABORADOR_ACTIVO = 1;
    private final int ESTATUS_COLABORADOR_SUSPENDIDO = 3;
    
    public AutenticacionDM(IAutenticacionRepository contrato){
        this.contrato = contrato;
    }
    
    public Autenticacion verificarCredencialesColaborador(String noPersonal, String password) throws RuntimeException{
        Autenticacion respuesta = new Autenticacion();
        if(noPersonal.isEmpty() || password.isEmpty()){
            respuesta.setCredencialesCorrectas(false);
            respuesta.setMensaje("Los campos no son validos");
            return respuesta;
        }
        String passwordCifrado = Utilidades.generarCifradoSHA256(password);
        int idColaborador = contrato.verificarCredencias(noPersonal, passwordCifrado);
        if(idColaborador == CREDENCIALES_INCORRECTAS){
            respuesta.setCredencialesCorrectas(false);
            respuesta.setMensaje("No. Personal y/o contrraseña incorrectos. Favor de verificar la informacion");
            return respuesta;
        }
        
        ColaboradorEstatus estatus = contrato.verificarEstatusColaborador(idColaborador);
        
        if(estatus.getIdEstatus() == ESTATUS_COLABORADOR_ACTIVO){
            respuesta.setCredencialesCorrectas(true);
            respuesta.setActivo(true);
            Colaborador info = contrato.obtenerInformacionColaborador(idColaborador);
            respuesta.setColaborador(info);
            respuesta.setMensaje("Bienvenidx "+info.getNombre()+ " al sistema de gestion de pacientes.");
            return respuesta;
        }
        
        respuesta.setCredencialesCorrectas(true);
        respuesta.setActivo(false);
        if(estatus.getIdEstatus() == ESTATUS_COLABORADOR_SUSPENDIDO){
            respuesta.setMensaje("Lo sentimos tu cuenta ha sido baneada por puta");
        } else {
            respuesta.setMensaje("No. Personal y/o contrraseña incorrectos. Favor de verificar la informacion");
        }
        return respuesta;
    }
    
}
