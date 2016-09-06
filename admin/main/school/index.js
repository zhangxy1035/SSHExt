Ext.onReady(function(){
	//指定每页显示的条数
	var itemsPerPage = 2;
	//创建表格数据模型
	Ext.define('School', {
	    extend: 'Ext.data.Model',
	    fields: ['id','schoolName','schoolPhone','schoolAddress']
	});
	//定义数据源
	var schoolStore = Ext.create('Ext.data.Store',{
		autoLoad:{start:0,limit:itemsPerPage},
		model:'School',
		pageSize:itemsPerPage,
		proxy:{
			type:'ajax',
			url:'school/school_getSchoolInfo.action',
			reader:{
				type:'json',
				totalProperty:'results',
				root:'rows'
			}
		}
	});
	//创建工具栏
	var toolbar = [
	{text:'新增校区',iconCls:'add',handler:showAdd},
	{text:'修改校区',iconCls:'update',handler:showUpdate},
	{text:'删除校区',iconCls:'delete',handler:showDelete}
	];
	
	//创建表格
	var schoolGrid = Ext.create('Ext.grid.Panel',{
		tbar:toolbar,
		region:'center',
		store:schoolStore,
		selModel:new Ext.selection.CheckboxModel(),
		columns:[
		{text:'校区ID',width:80,dataIndex:'id',sortabel:true},
		{text:'校区名称',width:80,dataIndex:'schoolName'},
		{text:'校区电话',width:80,dataIndex:'schoolPhone'},
		{text:'校区地址',width:80,dataIndex:'schoolAddress'}
		],
		bbar:[{
			xtype:'pagingtoolbar',
			store: schoolStore,
			width:600,
			displayInfo:true
		}]
	});
	
	//创建viewport
	Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[schoolGrid]
	});
	
	//创建表单
	Ext.QuickTips.init();
	
	var schoolForm = Ext.create('Ext.form.Panel',{
		filedDefault:{
			labelSeparator:':',
			labelWidth:80,
			msgTarget:'side',
			width:300
		},
		bodyPadding:5,
		frame:true,
		items:[{
			xtype:'textfield',
			name:'school.schoolName',
			id:'schoolName',
			allowBlank:false,
			fieldLabel:'校区名称'
		},{
			xtype:'textfield',
			name:'school.schoolPhone',
			id:'schoolPhone',
			allowBlank:false,
			fieldLabel:'校区电话'
		},{
			xtype:'textfield',
			name:'school.schoolAddress',
			id:'schoolAddress',
			allowBlank:false,
			fieldLabel:'校区地址'
		},{
			xtype:'hidden',
			name:'school.id',
			id:'id'
		}],
		buttons:[{text:'提交',handler:submitForm},
		        {text:'关闭',handler:function(){win.hide();}}
		]
	});
	
	//创建窗口
	var win = Ext.create('Ext.window.Window',{
		layout:'fit',
		width:300,
		closeAction:'hide',
		height:280,
		resizeavle:false,
		closeable:true,
		items:[schoolForm]
	});
	
	function submitForm() {
		if(schoolForm.isAdd) {
		schoolForm.form.submit({
			clientValidation:true,
			waitMsg:'正在提交数据请稍后',
			waitTitle:'提示',
			url:'school/school_add.action',
			method:'POST',
			success:function(form,action) {
				win.hide();
				Ext.Msg.alert('提示','添加校区成功');
				schoolStore.load();
			},
			failure:function(form,action) {
				Ext.Msg.alert('提示','添加校区失败');
			}
		});
		} else {
			schoolForm.form.submit({
				clientValidation:true,
				waitMsg:'正在提交数据请稍后',
				waitTitle:'提示',
				url:'school/school_update.action',
				method:'POST',
				success:function(form,action) {
					win.hide();
					Ext.Msg.alert('提示','修改校区成功');
					schoolStore.load();
				},
				failure:function(form,action) {
					Ext.Msg.alert('提示','修改校区失败');
				}
			});
		}
	}
	
	//显示添加
	function showAdd() {
		schoolForm.form.reset();
		schoolForm.isAdd = true;
		win.setTitle('新增校区');
		win.show();
	}
	
	//显示修改
	function showUpdate() {
		//获取对应的id
		var idList = getSchoolIdList();
		var num = idList.length;
		if(num>1) {
			Ext.Msg.alert('提示','每次只能操作一条数据');
		} else {
			schoolForm.form.reset();
			win.setTitle('修改校区');
			win.show();
			loadForm(idList[0]);
		}
	}
	
	function getSchoolIdList() {
		var recs = schoolGrid.getSelectionModel().getSelection();
		var idList = [];
		if(recs.length==0) {
			Ext.Msg.alert('提示','请选择你需要操作的数据');
			return;
		} else {
			for(var i=0;i<recs.length;i++) {
				var rec = recs[i];
				idList.push(rec.get('id'));
			}
		}
		return idList;
	}
	
	function loadForm(id) {
		schoolForm.form.load({
			waitMsg:'正在加载数据请稍后',
			waitTitle:'提示',
			url:'school/school_getSchoolById.action',
			params:{id:id},
			failure:function(form,action) {
				Ext.Msg.alert('提示','修改校区失败');
			}
		});
	}
	
	function showDelete() {
		var idList = getSchoolIdList();
		var num = idList.length;
		if(num==0) {
			Ext.Msg.alert('提示','请选择你需要操作的数据');
			return;
		} else {
			Ext.Msg.confirm('提示','确定删除对应的数据吗？',function(btn) {
				if(btn=='yes') {
					deleteSchool(idList);
				}
			});
		}
	}
	
	function deleteSchool(idList) {
		var ids = idList.join(',');
		var msgTip = Ext.Msg.show({
			title:'提示',
			width:250,
			msg:'正在删除数据请稍后...'
		});
		Ext.Ajax.request({
			url:'school/school_delete.action',
			params:{ids:ids},
			method:'POST',
			success:function(form,action) {
				msgTip.hide();
				Ext.Msg.alert('提示','删除校区成功');
				schoolStore.load();
			},
			failure:function(form,action) {
				msgTip.hide();
				Ext.Msg.alert('提示','删除校区失败');
			}
		});
		
	}
});