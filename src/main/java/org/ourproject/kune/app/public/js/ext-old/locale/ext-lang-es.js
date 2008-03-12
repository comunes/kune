/*
 * Ext - JS Library 1.0 Alpha 3 - Rev 4
 * Copyright(c) 2006-2007, Jack Slocum.
 * 
 * http://www.extjs.com/license.txt
 */


/*
 * Spanish translation by Gustavo Picón
 */

Ext.UpdateManager.defaults.indicatorText = '<div class="loading-indicator">Cargando...</div>';

Date.monthNames =
   ["Enero",
    "Febrero",
    "Marzo",
    "Abril",
    "Mayo",
    "Junio",
    "Julio",
    "Agosto",
    "Setiembre",
    "Octubre",
    "Noviembre",
    "Diciembre"];

Date.dayNames =
   ["Domingo",
    "Lunes",
    "Martes",
    "Miércoles",
    "Jueves",
    "Viernes",
    "Sábado"];

Ext.apply(Ext.DatePicker.prototype, {
    todayText : "Hoy",
    todayTip : "{0} (Barra espaciadora)",
    minText : "Esta fecha es anterior a la fecha mínima",
    maxText : "Esta fecha es posterior a la fecha máxima",
    format : "m/d/y",
    disabledDaysText : "",
    disabledDatesText : "",
    monthNames : Date.monthNames,
    dayNames : Date.dayNames,
    nextText: 'Mes Siguiente (Control+Derecha)',
    prevText: 'Mes Anterior (Control+Izquierda)',
    monthYearText: 'Escoja un mes (Control+Arriba/Abajo para cambiar años)'
});

Ext.MessageBox.buttonText = {
    ok : "Aceptar",
    cancel : "Cancelar",
    yes : "Sí",
    no : "No"
};

Ext.apply(Ext.PagingToolbar.prototype, {
    beforePageText : "Página",
    afterPageText : "de {0}",
    firstText : "Primera Página",
    prevText : "Página Anterior",
    nextText : "Página Siguiente",
    lastText : "Última Página",
    refreshText : "Refrescar"
});

Ext.TabPanelItem.prototype.closeText = "Cerrar esta pestaña";
