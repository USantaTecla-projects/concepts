"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[854],{3854:(B,l,o)=>{o.r(l),o.d(l,{CorrectionPendingListModule:()=>N});var s=o(9808),p=o(1994),d=o(3607),m=o(1787),C=o(4004),_=o(5577),f=o(6945);class P extends f.J{constructor(i,e){super(),this.examService=i,this.authStore=e}fetchData(i){return this.examService.getUserExams(this.authStore.user$,i,!1)}}var n=o(5e3),u=o(5813),x=o(4607),M=o(2350),O=o(2138),h=o(4594),v=o(5245),c=o(1125),y=o(9520),Z=o(4623),b=o(7423),T=o(5899);function L(t,i){if(1&t){const e=n.EpF();n.TgZ(0,"div",13)(1,"div",14)(2,"mat-icon",15),n._uU(3,"description"),n.qZA(),n.TgZ(4,"div",16)(5,"div",17),n._uU(6),n.qZA(),n.TgZ(7,"div",18),n._uU(8),n.qZA(),n.TgZ(9,"div",19),n._uU(10,"22/02/2020"),n.qZA()()(),n.TgZ(11,"button",20),n.NdJ("click",function(){n.CHM(e);const r=n.oxw().element;return n.oxw(3).onExamCorrection(r.id)}),n._uU(12," Correct "),n.TgZ(13,"mat-icon"),n._uU(14,"edit_note"),n.qZA()()()}if(2&t){const e=n.oxw().element;n.xp6(6),n.hij("Cristian de Gracia Nuero ",e.userID,""),n.xp6(2),n.hij("Exam #",e.id,"")}}function S(t,i){1&t&&(n.TgZ(0,"div",13),n._UZ(1,"mat-progress-bar",21),n.qZA())}function A(t,i){if(1&t&&(n.YNc(0,L,15,2,"div",11),n.YNc(1,S,2,0,"ng-template",null,12,n.W1O)),2&t){const e=i.element,a=n.MAs(2);n.Q6J("ngIf",e)("ngIfElse",a)}}function U(t,i){if(1&t&&(n.TgZ(0,"app-infinite-scroll-list",9),n.YNc(1,A,3,2,"ng-template",null,10,n.W1O),n.qZA()),2&t){const e=n.oxw(2);n.Q6J("elementSize",120)("datasource",e.examItemDatasource)}}function I(t,i){1&t&&(n.TgZ(0,"div",22)(1,"mat-icon"),n._uU(2,"rule"),n.qZA(),n.TgZ(3,"span",23),n._uU(4,"No exams to correct yet"),n.qZA(),n._UZ(5,"br"),n.TgZ(6,"span",24),n._uU(7,"Congratulations! You have no more work to do."),n.qZA()())}function E(t,i){if(1&t&&(n.ynx(0),n.YNc(1,U,3,2,"app-infinite-scroll-list",7),n.YNc(2,I,8,0,"ng-template",null,8,n.W1O),n.BQk()),2&t){const e=i.ngIf,a=n.MAs(3);n.xp6(1),n.Q6J("ngIf",e.length)("ngIfElse",a)}}const z=[{path:"",component:(()=>{class t{constructor(e,a,r,g,J){this.router=e,this.examService=a,this.correctionInCourseStore=r,this.authStore=g,this.snackbarService=J}ngOnInit(){this.examItemDatasource=new P(this.examService,this.authStore),this.hasRepliedExams$=this.examService.getUserExams(this.authStore.user$,0,!1)}onExamCorrection(e){this.authStore.user$.pipe((0,C.U)(a=>a?a.id:-1),(0,_.z)(a=>this.correctionInCourseStore.getExam(a,e))).subscribe({next:()=>this.router.navigateByUrl("/correction/in-course"),error:a=>this.snackbarService.openSnackBar(a.message)})}}return t.\u0275fac=function(e){return new(e||t)(n.Y36(m.F0),n.Y36(u.O),n.Y36(x.m),n.Y36(M.C),n.Y36(O.o))},t.\u0275cmp=n.Xpm({type:t,selectors:[["app-correction-pending-list"]],decls:17,vars:5,consts:[["color","primary",1,"tool-bar"],["mat-button","","disabled",""],[1,"icon"],[1,"container"],["hideToggle","",3,"disabled","expanded"],["expandedHeight","8rem"],[4,"ngIf"],["class","pending-exam-list",3,"elementSize","datasource",4,"ngIf","ngIfElse"],["noExamsYet",""],[1,"pending-exam-list",3,"elementSize","datasource"],["elementTemplate",""],["class","list-item",4,"ngIf","ngIfElse"],["noExam",""],[1,"list-item"],[1,"item-decoration"],["mat-list-icon",""],[1,"info"],[1,"username"],[1,"exam-name"],[1,"date"],["data-cy","correction-correct-button","mat-raised-button","","color","primary",1,"submit-button",3,"click"],["mode","indeterminate"],[1,"empty-history"],[1,"first-empty-title"],[1,"second-empty-title"]],template:function(e,a){if(1&e&&(n.TgZ(0,"mat-toolbar",0)(1,"div",1)(2,"mat-icon",2),n._uU(3,"draw"),n.qZA(),n.TgZ(4,"span"),n._uU(5,"Correction"),n.qZA()()(),n.TgZ(6,"div",3)(7,"mat-accordion")(8,"mat-expansion-panel",4)(9,"mat-expansion-panel-header",5)(10,"mat-panel-title")(11,"mat-icon",2),n._uU(12,"edit_note"),n.qZA(),n.TgZ(13,"h3"),n._uU(14,"Pending exams"),n.qZA()()(),n.YNc(15,E,4,2,"ng-container",6),n.ALo(16,"async"),n.qZA()()()),2&e){let r;n.xp6(8),n.Q6J("disabled",!0)("expanded",!0),n.xp6(7),n.Q6J("ngIf",null==(r=n.lcZ(16,3,a.hasRepliedExams$))?null:r.content)}},directives:[h.Ye,v.Hw,c.pp,c.ib,c.yz,c.yK,s.O5,y.G,Z.Nh,b.lW,T.pW],pipes:[s.Ov],styles:[".container[_ngcontent-%COMP%]{margin:3rem 18rem}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]{background-color:#673ab7}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   .mat-expansion-panel-header[aria-disabled=true][_ngcontent-%COMP%]{color:#fff}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   mat-panel-title[_ngcontent-%COMP%]{display:flex;margin-left:.75rem}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   mat-panel-title[_ngcontent-%COMP%]   mat-icon[_ngcontent-%COMP%]{width:2.5rem;height:2.5rem;font-size:2.5rem}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   mat-panel-title[_ngcontent-%COMP%]   h3[_ngcontent-%COMP%]{margin:.5rem;font-size:1.5rem;font-weight:500}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   .pending-exam-list[_ngcontent-%COMP%]   .list-item[_ngcontent-%COMP%]{border:1px solid #dbdbdb;border-radius:.25rem;padding:0 2rem;height:120px;display:flex;align-items:center;justify-content:space-between}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   .pending-exam-list[_ngcontent-%COMP%]   .list-item[_ngcontent-%COMP%]   .item-decoration[_ngcontent-%COMP%]{display:flex;align-items:center;width:80%}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   .pending-exam-list[_ngcontent-%COMP%]   .list-item[_ngcontent-%COMP%]   .item-decoration[_ngcontent-%COMP%]   mat-icon[_ngcontent-%COMP%]{margin-right:2rem;font-size:2rem}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   .pending-exam-list[_ngcontent-%COMP%]   .list-item[_ngcontent-%COMP%]   .item-decoration[_ngcontent-%COMP%]   .info[_ngcontent-%COMP%]{display:flex;flex-direction:column;justify-content:center}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   .pending-exam-list[_ngcontent-%COMP%]   .list-item[_ngcontent-%COMP%]   .item-decoration[_ngcontent-%COMP%]   .info[_ngcontent-%COMP%]   .username[_ngcontent-%COMP%]{font-weight:500;font-size:1.15rem}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   .pending-exam-list[_ngcontent-%COMP%]   .list-item[_ngcontent-%COMP%]   .item-decoration[_ngcontent-%COMP%]   .info[_ngcontent-%COMP%]   .date[_ngcontent-%COMP%]{margin-top:.4rem}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   .pending-exam-list[_ngcontent-%COMP%]   .list-item[_ngcontent-%COMP%]   .item-decoration[_ngcontent-%COMP%]   .info[_ngcontent-%COMP%]   .exam-name[_ngcontent-%COMP%]{font-weight:500;color:#6b6b6b}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   .pending-exam-list[_ngcontent-%COMP%]   .list-item[_ngcontent-%COMP%]   button[_ngcontent-%COMP%]{height:3rem}.container[_ngcontent-%COMP%]     .mat-expansion-panel-body{background-color:#fff!important}.no-exam[_ngcontent-%COMP%]{display:flex;align-items:center}.empty-history[_ngcontent-%COMP%]{display:flex;flex-direction:column;align-items:center;margin-top:4rem;margin-bottom:6rem}.empty-history[_ngcontent-%COMP%]   mat-icon[_ngcontent-%COMP%]{width:10rem;height:10rem;font-size:10rem;color:#bfbfbf;margin-bottom:1rem}.empty-history[_ngcontent-%COMP%]   .first-empty-title[_ngcontent-%COMP%]{font-size:1.75rem;color:#bfbfbf;font-weight:500;text-align:center}.empty-history[_ngcontent-%COMP%]   .second-empty-title[_ngcontent-%COMP%]{font-size:1rem;color:#bfbfbf;font-weight:500;text-align:center}"]}),t})()}];let Y=(()=>{class t{}return t.\u0275fac=function(e){return new(e||t)},t.\u0275mod=n.oAB({type:t}),t.\u0275inj=n.cJS({imports:[[m.Bz.forChild(z)],m.Bz]}),t})(),N=(()=>{class t{}return t.\u0275fac=function(e){return new(e||t)},t.\u0275mod=n.oAB({type:t}),t.\u0275inj=n.cJS({imports:[[s.ez,Y,d.m,p.q]]}),t})()}}]);