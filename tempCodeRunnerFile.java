//事件类
class MyActionListener implements ActionListener{
    MyFrame frame=null;
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand()=="插入"){
            if(this.frame.T==0){
                this.frame.myPanel_insert.fatherJTextArea.setEnabled(false);
            }
            else{
                this.frame.myPanel_insert.fatherJTextArea.setEnabled(true);
            }
            this.frame.myPanel_insert.addressJTextArea.setText("");
            this.frame.myPanel_insert.bdayJTextArea.setText("");
            this.frame.myPanel_insert.bmonthJTextArea.setText("");
            this.frame.myPanel_insert.byearJTextArea.setText("");
            this.frame.myPanel_insert.ddayJTextArea.setText("");
            this.frame.myPanel_insert.dmonthJTextArea.setText("");
            this.frame.myPanel_insert.dyearJTextArea.setText("");
            this.frame.myPanel_insert.fatherJTextArea.setText("");
            this.frame.myPanel_insert.nameJTextArea.setText("");
            this.frame.container.remove(this.frame.myPanel_init.panel_init);
            this.frame.container.add(this.frame.myPanel_insert.panel_insert);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="查找"){
            this.frame.container.remove(this.frame.myPanel_init.panel_init);
            this.frame.container.add(this.frame.myPanel_search.panel_search);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="删除"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_delete.panel_delte);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="关系溯源"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_relation.panel_relation);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="创建日期"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_creatTime.panel_createTime);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="修改日期"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_modifyTime.panel_createTime);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="排序"){
            JavaGUI ctj=new JavaGUI();
            boolean error=false;
            try{
                this.frame.myPanel_sortByBirth.sortByBirthJLabel.setText(ctj.sortByBirth(this.frame.T));
                this.frame.myPanel_sortByBirth.sortByBirthJLabel.setBounds(20,20,this.frame.myPanel_sortByBirth.panel_sortByBirth.getWidth(),this.frame.myPanel_sortByBirth.panel_sortByBirth.getHeight());
                this.frame.myPanel_sortByBirth.sortByBirthJLabel.setFont(new Font("宋体",Font.BOLD,((this.frame.myPanel_sortByBirth.panel_sortByBirth.getWidth())/(this.frame.myPanel_sortByBirth.sortByBirthJLabel.getText().length()))));
                this.frame.container.removeAll();
                this.frame.container.add(this.frame.myPanel_sortByBirth.panel_sortByBirth);
                this.frame.container.revalidate();
                this.frame.container.repaint();
            }catch(Exception ee){
                error=true;
            }finally{
                if(error){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
                }
            }
        }
        else if(e.getActionCommand()=="提醒生日"){
            JavaGUI ctj=new JavaGUI();
            if(this.frame.Date==0){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
            }
            this.frame.myPanel_remindBirth.remindJLabel.setText(ctj.remindBirth(this.frame.T, this.frame.Date));
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_remindBirth.panel_remindBirth);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="提交"){
            boolean alive;
            boolean error=false;
            int bday=-1,byear=-1,bmonth=-1;
            int dday=-1,dmonth=-1,dyear=-1;
            JavaGUI ctj=new JavaGUI();
            String father=this.frame.myPanel_insert.fatherJTextArea.getText();
            String name=this.frame.myPanel_insert.nameJTextArea.getText();
            try {
                byear=Integer.parseInt(this.frame.myPanel_insert.byearJTextArea.getText());
                bmonth=Integer.parseInt(this.frame.myPanel_insert.bmonthJTextArea.getText());
                bday=Integer.parseInt(this.frame.myPanel_insert.bdayJTextArea.getText());
            } catch (Exception ee) {
                error=true;
            }finally{
                if(error)
                {
                    this.frame.container.remove(this.frame.myPanel_insert.panel_insert);
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
                }

            }
            String address=this.frame.myPanel_insert.addressJTextArea.getText();
            
            if(this.frame.myPanel_insert.aliveButtonGroup.getSelection()==this.frame.myPanel_insert.aliveYesJRadioButton.getModel()){
                alive=true;
            }
            else{
                alive=false;
                try {
                    dyear=Integer.parseInt(this.frame.myPanel_insert.dyearJTextArea.getText());
                    dmonth=Integer.parseInt(this.frame.myPanel_insert.dmonthJTextArea.getText());
                    dday=Integer.parseInt(this.frame.myPanel_insert.ddayJTextArea.getText());
                } catch (Exception ee) {
                    error=true;
                }finally{
                    if(error)
                {
                    this.frame.container.remove(this.frame.myPanel_insert.panel_insert);
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
                }
                }
            }
            long TN=ctj.insert(this.frame.T, father, name, byear, bmonth, bday, alive, address, alive, dyear, dmonth, dday);
            if(TN!=0)
                this.frame.T=TN;
            else{
                this.frame.container.remove(this.frame.myPanel_insert.panel_insert);
                this.frame.container.add(this.frame.myPanel_error.panel_error);
                this.frame.container.revalidate();
                this.frame.container.repaint();
                return;
            }
            this.frame.container.remove(this.frame.myPanel_insert.panel_insert);
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="返回"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="插入否"){
            if(this.frame.myPanel_insert.aliveNoJRadioButton.isSelected()){
                this.frame.myPanel_insert.ddayJTextArea.setEnabled(true);
                this.frame.myPanel_insert.dmonthJTextArea.setEnabled(true);
                this.frame.myPanel_insert.dyearJTextArea.setEnabled(true);
            }
        }
        else if(e.getActionCommand()=="插入是"){
            if(this.frame.myPanel_insert.aliveYesJRadioButton.isSelected()){
                this.frame.myPanel_insert.ddayJTextArea.setText("");
                this.frame.myPanel_insert.dmonthJTextArea.setText("");
                this.frame.myPanel_insert.dyearJTextArea.setText("");
                this.frame.myPanel_insert.ddayJTextArea.setEnabled(false);
                this.frame.myPanel_insert.dmonthJTextArea.setEnabled(false);
                this.frame.myPanel_insert.dyearJTextArea.setEnabled(false);

            }
        }
        else if(e.getActionCommand()=="修改否"){
            if(this.frame.myPanel_information.aliveNoJRadioButton.isSelected()){
                this.frame.myPanel_information.ddayJTextArea.setEnabled(true);
                this.frame.myPanel_information.dmonthJTextArea.setEnabled(true);
                this.frame.myPanel_information.dyearJTextArea.setEnabled(true);
            }
        }
        else if(e.getActionCommand()=="修改是"){
            if(this.frame.myPanel_information.aliveYesJRadioButton.isSelected()){
                this.frame.myPanel_information.ddayJTextArea.setText("");
                this.frame.myPanel_information.dmonthJTextArea.setText("");
                this.frame.myPanel_information.dyearJTextArea.setText("");
                this.frame.myPanel_information.ddayJTextArea.setEnabled(false);
                this.frame.myPanel_information.dmonthJTextArea.setEnabled(false);
                this.frame.myPanel_information.dyearJTextArea.setEnabled(false);

            }
        }
        else if(e.getActionCommand()=="按名字搜索"){
            this.frame.myPanel_searchByName.nameJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_searchByName.panel_searchByName);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if (e.getActionCommand()=="按生日搜索"){
            this.frame.myPanel_searchByBirth.byearJTextArea.setText("");
            this.frame.myPanel_searchByBirth.bmonthJTextArea.setText("");
            this.frame.myPanel_searchByBirth.bdayJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_searchByBirth.panel_searchByBirth);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="依据名字搜索"){
            JavaGUI.TNode T1;
            String name=this.frame.myPanel_searchByName.nameJTextArea.getText();
            JavaGUI ctj=new JavaGUI();
            long TN=ctj.searchByName(this.frame.T, name);
            if(TN!=0){
                this.frame.TN=TN;
            }
            else{
                this.frame.container.removeAll();
                this.frame.container.add(this.frame.myPanel_error.panel_error);
                this.frame.container.revalidate();
                this.frame.container.repaint();
                return;
            }
            T1=ctj.convertToTree(this.frame.TN);
            
            if(T1.parent==0){
                this.frame.myPanel_information.fatherJTextArea.setText("");
            }
            else{
                this.frame.myPanel_information.fatherJTextArea.setText(ctj.convertToTree(T1.parent).name);
            }
            
            this.frame.myPanel_information.nameJTextArea.setText(T1.name);
            this.frame.myPanel_information.addressJTextArea.setText(T1.address);
            this.frame.myPanel_information.aliveYesJRadioButton.setSelected(T1.alive);
            this.frame.myPanel_information.aliveNoJRadioButton.setSelected(!T1.alive);
            this.frame.myPanel_information.byearJTextArea.setText(Integer.toString(T1.birth.year));
            this.frame.myPanel_information.bmonthJTextArea.setText(Integer.toString(T1.birth.month));
            this.frame.myPanel_information.bdayJTextArea.setText(Integer.toString(T1.birth.day));
            
            if(T1.death.year==-1){
                this.frame.myPanel_information.dyearJTextArea.setText("");
                this.frame.myPanel_information.dmonthJTextArea.setText("");
                this.frame.myPanel_information.ddayJTextArea.setText("");
            }
            else{
                this.frame.myPanel_information.dyearJTextArea.setText(Integer.toString(T1.death.year));
                this.frame.myPanel_information.dmonthJTextArea.setText(Integer.toString(T1.death.month));
                this.frame.myPanel_information.ddayJTextArea.setText(Integer.toString(T1.death.day));
            }
            
            //不可修改
            this.frame.myPanel_information.fatherJTextArea.setEnabled(false);
            this.frame.myPanel_information.nameJTextArea.setEnabled(false);
            this.frame.myPanel_information.addressJTextArea.setEnabled(false);
            this.frame.myPanel_information.aliveYesJRadioButton.setEnabled(false);
            this.frame.myPanel_information.aliveNoJRadioButton.setEnabled(false);
            this.frame.myPanel_information.byearJTextArea.setEnabled(false);
            this.frame.myPanel_information.bmonthJTextArea.setEnabled(false);
            this.frame.myPanel_information.bdayJTextArea.setEnabled(false);
            this.frame.myPanel_information.dyearJTextArea.setEnabled(false);
            this.frame.myPanel_information.dmonthJTextArea.setEnabled(false);
            this.frame.myPanel_information.ddayJTextArea.setEnabled(false);


            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_information.panel_information);
            this.frame.container.revalidate();
            this.frame.container.repaint();
            
        }
        else if(e.getActionCommand()=="依据生日搜索"){
            boolean error=false;
            JavaGUI.TNode T1;
            int byear=-1,bmonth=-1,bday=-1;
            try {
                byear=Integer.parseInt(this.frame.myPanel_searchByBirth.byearJTextArea.getText());
                bmonth=Integer.parseInt(this.frame.myPanel_searchByBirth.bmonthJTextArea.getText());
                bday=Integer.parseInt(this.frame.myPanel_searchByBirth.bdayJTextArea.getText());
            } catch (Exception ee) {
                error=true;
            }finally{
                if(error){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
                }
            }
            
            JavaGUI ctj=new JavaGUI();
            long TN=ctj.searchByBirth(this.frame.T, byear, bmonth, bday);
            if(TN!=0){
                this.frame.TN=TN;
            }
            else{
                this.frame.container.removeAll();
                this.frame.container.add(this.frame.myPanel_error.panel_error);
                this.frame.container.revalidate();
                this.frame.container.repaint();
                return;
            }
            this.frame.container.add(this.frame.myPanel_information.panel_information);
            T1=ctj.convertToTree(this.frame.TN);
            if(T1.parent==0){
                this.frame.myPanel_information.fatherJTextArea.setText("");
            }
            else{
                this.frame.myPanel_information.fatherJTextArea.setText(ctj.convertToTree(T1.parent).name);
            }
            this.frame.myPanel_information.nameJTextArea.setText(T1.name);
            this.frame.myPanel_information.addressJTextArea.setText(T1.address);
            this.frame.myPanel_information.aliveYesJRadioButton.setSelected(T1.alive);
            this.frame.myPanel_information.aliveNoJRadioButton.setSelected(!T1.alive);
            this.frame.myPanel_information.byearJTextArea.setText(Integer.toString(T1.birth.year));
            this.frame.myPanel_information.bmonthJTextArea.setText(Integer.toString(T1.birth.month));
            this.frame.myPanel_information.bdayJTextArea.setText(Integer.toString(T1.birth.day));
            if(T1.death.year==-1){
                this.frame.myPanel_information.dyearJTextArea.setText("");
            this.frame.myPanel_information.dmonthJTextArea.setText("");
            this.frame.myPanel_information.ddayJTextArea.setText("");
            }
            else{
                this.frame.myPanel_information.dyearJTextArea.setText(Integer.toString(T1.death.year));
                this.frame.myPanel_information.dmonthJTextArea.setText(Integer.toString(T1.death.month));
                this.frame.myPanel_information.ddayJTextArea.setText(Integer.toString(T1.death.day));
            }
            //不可修改
            this.frame.myPanel_information.fatherJTextArea.setEnabled(false);
            this.frame.myPanel_information.nameJTextArea.setEnabled(false);
            this.frame.myPanel_information.addressJTextArea.setEnabled(false);
            this.frame.myPanel_information.aliveYesJRadioButton.setEnabled(false);
            this.frame.myPanel_information.aliveNoJRadioButton.setEnabled(false);
            this.frame.myPanel_information.byearJTextArea.setEnabled(false);
            this.frame.myPanel_information.bmonthJTextArea.setEnabled(false);
            this.frame.myPanel_information.bdayJTextArea.setEnabled(false);
            this.frame.myPanel_information.dyearJTextArea.setEnabled(false);
            this.frame.myPanel_information.dmonthJTextArea.setEnabled(false);
            this.frame.myPanel_information.ddayJTextArea.setEnabled(false);
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_information.panel_information);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="修改"){
            this.frame.myPanel_information.nameJTextArea.setEnabled(true);
            this.frame.myPanel_information.addressJTextArea.setEnabled(true);
            this.frame.myPanel_information.aliveYesJRadioButton.setEnabled(true);
            this.frame.myPanel_information.aliveNoJRadioButton.setEnabled(true);
            this.frame.myPanel_information.byearJTextArea.setEnabled(true);
            this.frame.myPanel_information.bmonthJTextArea.setEnabled(true);
            this.frame.myPanel_information.bdayJTextArea.setEnabled(true);
            if(this.frame.myPanel_information.aliveNoJRadioButton.isSelected()){
                this.frame.myPanel_information.dyearJTextArea.setEnabled(true);
                this.frame.myPanel_information.dmonthJTextArea.setEnabled(true);
                this.frame.myPanel_information.ddayJTextArea.setEnabled(true);
            }
            
            //按钮更换
            this.frame.myPanel_information.panel_information.remove(this.frame.myPanel_information.informationButton.b_modify);
            this.frame.myPanel_information.panel_information.add(this.frame.myPanel_information.informationButton.b_confirm);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="确认"){
            boolean alive,error=false;
            int dday=-1,dmonth=-1,dyear=-1,byear=-1,bmonth=-1,bday=-1;
            JavaGUI ctj=new JavaGUI();
            this.frame.myPanel_information.panel_information.remove(this.frame.myPanel_information.informationButton.b_confirm);
            this.frame.myPanel_information.panel_information.add(this.frame.myPanel_information.informationButton.b_modify);
            String name=this.frame.myPanel_information.nameJTextArea.getText();
            try {
                byear=Integer.parseInt(this.frame.myPanel_information.byearJTextArea.getText());
                bmonth=Integer.parseInt(this.frame.myPanel_information.bmonthJTextArea.getText());
                bday=Integer.parseInt(this.frame.myPanel_information.bdayJTextArea.getText());
            } catch (Exception ee) {
                error=true;
            }finally{
                if(error){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
                }
            }
            
            String address=this.frame.myPanel_information.addressJTextArea.getText();
            
            if(this.frame.myPanel_information.aliveYesJRadioButton.isSelected()){
                alive=true;
            }
            else{
                alive=false;
                try {
                    dyear=Integer.parseInt(this.frame.myPanel_information.dyearJTextArea.getText());
                    dmonth=Integer.parseInt(this.frame.myPanel_information.dmonthJTextArea.getText());
                    dday=Integer.parseInt(this.frame.myPanel_information.ddayJTextArea.getText());
                } catch (Exception ee) {
                    error=true;
                }finally{
                    if(error){
                        this.frame.container.removeAll();
                        this.frame.container.add(this.frame.myPanel_error.panel_error);
                        this.frame.container.revalidate();
                        this.frame.container.repaint();
                        return;
                    }
                }
            }
            this.frame.TN=ctj.modify(this.frame.TN, name, byear, bmonth, bday, alive, address, alive, dyear, dmonth, dday);
            this.frame.myPanel_information.panel_information.remove(this.frame.myPanel_information.informationButton.b_confirm);
            this.frame.myPanel_information.panel_information.add(this.frame.myPanel_information.informationButton.b_modify);
            this.frame.container.remove(this.frame.myPanel_information.panel_information);
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="返回上一级"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_search.panel_search);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="删除中的删除"){
            String name=this.frame.myPanel_delete.nameJTextArea.getText();
            JavaGUI ctj=new JavaGUI();
            this.frame.T=ctj.delete(this.frame.T, name);
            this.frame.myPanel_delete.nameJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();

        }
        else if(e.getActionCommand()=="取消"){
            this.frame.myPanel_delete.nameJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="查询"){
            JavaGUI ctj=new JavaGUI();
            String name_1=this.frame.myPanel_relation.name_1JTextArea.getText();
            String name_2=this.frame.myPanel_relation.name_2JTextArea.getText();
            this.frame.myPanel_relation.relationJLabel.setText(ctj.relation(this.frame.T, name_1, name_2));
        }
        else if(e.getActionCommand()=="查询返回"){
            this.frame.myPanel_relation.relationJLabel.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="创建"){
            JavaGUI ctj=new JavaGUI();
            int year=-1,month=-1,day=-1;
            boolean error=false;
            try {
                year=Integer.parseInt(this.frame.myPanel_creatTime.yearJTextArea.getText());
                month=Integer.parseInt(this.frame.myPanel_creatTime.monthJTextArea.getText());
                day=Integer.parseInt(this.frame.myPanel_creatTime.dayJTextArea.getText());
            } catch (Exception ee) {
                error=true;
            }finally{
                if(error){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    this.frame.myPanel_creatTime.yearJTextArea.setText("");
                    this.frame.myPanel_creatTime.monthJTextArea.setText("");
                    this.frame.myPanel_creatTime.dayJTextArea.setText("");

                    return;
                }
            }
            
            this.frame.Date=ctj.createTime(year, month, day);
            String time=year+"/"+month+"/"+day;
            this.frame.myPanel_init.time.setText(time);
            this.frame.myPanel_creatTime.yearJTextArea.setText("");
            this.frame.myPanel_creatTime.monthJTextArea.setText("");
            this.frame.myPanel_creatTime.dayJTextArea.setText("");
            this.frame.myPanel_init.panel_init.remove(this.frame.myPanel_init.initButton.b_creatTime);
            this.frame.myPanel_init.panel_init.add(this.frame.myPanel_init.initButton.b_modifyTime);
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="创建中的返回"){
            this.frame.myPanel_creatTime.yearJTextArea.setText("");
            this.frame.myPanel_creatTime.monthJTextArea.setText("");
            this.frame.myPanel_creatTime.dayJTextArea.setText("");
            this.frame.myPanel_modifyTime.yearJTextArea.setText("");
            this.frame.myPanel_modifyTime.monthJTextArea.setText("");
            this.frame.myPanel_modifyTime.dayJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="确认修改时间"){
            JavaGUI ctj=new JavaGUI();
            int year=-1,month=-1,day=-1;
            boolean error=false;
            try {
                year=Integer.parseInt(this.frame.myPanel_modifyTime.yearJTextArea.getText());
                month=Integer.parseInt(this.frame.myPanel_modifyTime.monthJTextArea.getText());
                day=Integer.parseInt(this.frame.myPanel_modifyTime.dayJTextArea.getText());
            this.frame.Date=ctj.modifyDate(this.frame.Date, year, month, day);
            } catch (Exception ee) {
                error=true;
            }finally{
                if(error){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    this.frame.myPanel_modifyTime.yearJTextArea.setText("");
                    this.frame.myPanel_modifyTime.monthJTextArea.setText("");
                    this.frame.myPanel_modifyTime.dayJTextArea.setText("");
                    return;
                }
            }
            
            String time=year+"/"+month+"/"+day;
            this.frame.myPanel_init.time.setText(time);
            this.frame.myPanel_modifyTime.yearJTextArea.setText("");
            this.frame.myPanel_modifyTime.monthJTextArea.setText("");
            this.frame.myPanel_modifyTime.dayJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
    }
    public MyActionListener(MyFrame frame){
        this.frame=frame;
    }
}