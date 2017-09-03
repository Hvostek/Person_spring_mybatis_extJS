Ext.onReady(function(){

	//Ext.BLANK_IMAGE_URL = '/extjs-crud-grid/ext-3.2.1/resources/images/default/s.gif';
	
    var Contact = Ext.data.Record.create([
	{name: 'id'},
    {
        name: 'name',
        type: 'string'
    }, {
        name: 'surname',
        type: 'string'
    }, {
        name: 'email',
        type: 'string'
    }, {
        name: 'telephoneNumber',
        type: 'string'
    }, {
        name: 'personalData',
        type: 'string'
    }, {
        name: 'personalID',
        type: 'string'
    }]);
    
    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'contact/view.action',
            create : 'contact/create.action',
            update: 'contact/update.action',
            destroy: 'contact/delete.action'
        }
    });
    
    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    }, 
    Contact);

    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    
    var store = new Ext.data.Store({
        id: 'user',
        proxy: proxy,
        reader: reader,
        writer: writer,  
        autoSave: false 
    });

    //read the data from simple array
    store.load();
    
    Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
    	Ext.Msg.show({
    		title: 'ERROR',
    		msg: res.message,
    		icon: Ext.MessageBox.ERROR,
    		buttons: Ext.Msg.OK
    	});
    });

    
    var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    

    // create grid
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            {header: "NAME",
             width: 150,
             sortable: true,
             dataIndex: 'name',
             editor: {
                xtype: 'textfield',
                allowBlank: false
            }},
            {header: "SURNAME",
                width: 150,
                sortable: true,
                dataIndex: 'surname',
                editor: {
                   xtype: 'textfield',
                   allowBlank: false
             }},
            {header: "EMAIL",
             width: 150,
             sortable: true,
             dataIndex: 'email',
             editor: {
                xtype: 'textfield',
                allowBlank: false
            }},
            {header: "PHONE",
                width: 150,
                sortable: true,
                dataIndex: 'telephoneNumber',
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
               }},
               {header: "PERSONALDATA",
                   width: 160,
                   sortable: true,
                   dataIndex: 'personalData',
                   editor: {
                       xtype: 'textfield',
                       allowBlank: false
                  }},
                  {header: "PERSONID",
                      width: 160,
                      sortable: true,
                      dataIndex: 'personalID',
                      editor: {
                          xtype: 'textfield',
                          allowBlank: false
                     }},
        ],
        viewConfig:{forcefit:true},
        plugins: [editor],
        title: 'The list of Persons',
        height: 400,
        width:935,
		frame:false,
		tbar: [{
           iconCls: 'icon-user-add',
            text: 'ADD person',
            handler: function(){
                var e = new Contact({
                    name: 'New',
                    surname: 'Person',
                    email: 'new@yourdomain.com',
                    telephoneNumber: '89123456789',
                    personalData: '0123456 01/01/1901',
                    personalID: '1234567'
                    
                });
                editor.stopEditing();
                store.insert(0, e);
                grid.getView().refresh();
                grid.getSelectionModel().selectRow(0);
                editor.startEditing(0);
            }
        },{
           iconCls: 'icon-user-delete',
            text: 'DELETE Person',
            handler: function(){
                editor.stopEditing();
                var s = grid.getSelectionModel().getSelections();
                for(var i = 0, r; r = s[i]; i++){
                    store.remove(r);
                }
            }
        },{
           iconCls: 'icon-user-save',
            text: 'save all in DB',
            handler: function(){
                store.save();
            }
        }]
    });

    //render to DIV
    grid.render('crud-grid');
});