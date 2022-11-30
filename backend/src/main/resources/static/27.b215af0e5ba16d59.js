"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[27],{5027:(J,m,i)=>{i.r(m),i.d(m,{ProfileModule:()=>L});var r=i(9808),l=i(1994),g=i(3607),t=i(5e3);let p=(()=>{class n{}return n.\u0275fac=function(e){return new(e||n)},n.\u0275mod=t.oAB({type:n}),n.\u0275inj=t.cJS({providers:[],imports:[[r.ez,g.m,l.q]]}),n})();var f=i(1787),_=i(6945);class d extends _.J{constructor(o,e){super(),this.examService=o,this.authStore=e}fetchData(o){return this.examService.getUserExams(this.authStore.user$,o,!0)}}var u=i(5813),x=i(2350),P=i(4594),O=i(5245),s=i(1125),M=i(9520),C=i(6704),h=i(9224),y=i(5899);function v(n,o){if(1&n&&(t.TgZ(0,"mat-card")(1,"div",2)(2,"div",3)(3,"span"),t._uU(4),t.qZA()(),t.TgZ(5,"div",4)(6,"h4"),t._uU(7),t.qZA(),t.TgZ(8,"div")(9,"span",5),t._uU(10,"Date: "),t.qZA(),t.TgZ(11,"span"),t._uU(12),t.ALo(13,"date"),t.qZA()(),t.TgZ(14,"div")(15,"span",6),t._uU(16,"Correct answers: "),t.qZA(),t.TgZ(17,"span"),t._uU(18),t.qZA()()()()()),2&n){const e=t.oxw();t.xp6(4),t.Oqu(e.mark),t.xp6(3),t.Oqu(e.title),t.xp6(5),t.Oqu(t.lcZ(13,4,e.date)),t.xp6(6),t.Oqu(e.correctQuestions+"/"+e.questionList.length)}}function Z(n,o){1&n&&(t.TgZ(0,"mat-card",7),t._UZ(1,"mat-progress-bar",8),t.qZA())}let T=(()=>{class n{constructor(){this.isOffline=!1}set answerList(e){this.correctQuestions=e.filter(a=>a.correctionStatus===C.W.Correct).length}}return n.\u0275fac=function(e){return new(e||n)},n.\u0275cmp=t.Xpm({type:n,selectors:[["app-exam-history-item"]],inputs:{title:"title",mark:"mark",date:"date",timeSpent:"timeSpent",isOffline:"isOffline",questionList:"questionList",answerList:"answerList"},decls:3,vars:2,consts:[[4,"ngIf","ngIfElse"],["offline",""],[1,"exam-info"],[1,"mark"],[1,"text-info"],[1,"date-label"],[1,"correct-answers-label"],[1,"offline-card"],["mode","query"]],template:function(e,a){if(1&e&&(t.YNc(0,v,19,6,"mat-card",0),t.YNc(1,Z,2,0,"ng-template",null,1,t.W1O)),2&e){const c=t.MAs(2);t.Q6J("ngIf",!a.isOffline)("ngIfElse",c)}},directives:[r.O5,h.a8,y.pW],pipes:[r.uU],styles:["[_nghost-%COMP%]{z-index:99}mat-card[_ngcontent-%COMP%]{padding:0;height:128px!important;display:flex;box-shadow:#3c40434d 0 1px 2px,#3c404326 0 2px 6px 2px!important}mat-card[_ngcontent-%COMP%]   .exam-info[_ngcontent-%COMP%]{width:100%;display:flex}mat-card[_ngcontent-%COMP%]   .exam-info[_ngcontent-%COMP%]   .mark[_ngcontent-%COMP%]{display:flex;justify-content:center;align-items:center;background-color:#673ab7;color:#fff;width:8rem;height:8rem;border-radius:.25rem}mat-card[_ngcontent-%COMP%]   .exam-info[_ngcontent-%COMP%]   .mark[_ngcontent-%COMP%]   span[_ngcontent-%COMP%]{font-size:2rem;font-weight:500}mat-card[_ngcontent-%COMP%]   .exam-info[_ngcontent-%COMP%]   .text-info[_ngcontent-%COMP%]{margin:1rem}mat-card[_ngcontent-%COMP%]   .exam-info[_ngcontent-%COMP%]   .text-info[_ngcontent-%COMP%]   h4[_ngcontent-%COMP%]{font-weight:500;font-size:1.6rem;margin-bottom:.6rem}mat-card[_ngcontent-%COMP%]   .exam-info[_ngcontent-%COMP%]   .text-info[_ngcontent-%COMP%]   .date-label[_ngcontent-%COMP%], mat-card[_ngcontent-%COMP%]   .exam-info[_ngcontent-%COMP%]   .text-info[_ngcontent-%COMP%]   .correct-answers-label[_ngcontent-%COMP%], mat-card[_ngcontent-%COMP%]   .exam-info[_ngcontent-%COMP%]   .text-info[_ngcontent-%COMP%]   .time-spent-label[_ngcontent-%COMP%]{font-weight:500}mat-card[_ngcontent-%COMP%]   button[_ngcontent-%COMP%]{width:9rem}.offline-card[_ngcontent-%COMP%]{background-color:#f0f0f0;display:flex;align-items:center;justify-content:center}.offline-card[_ngcontent-%COMP%]   mat-progress-bar[_ngcontent-%COMP%]{width:95%}"]}),n})();function I(n,o){if(1&n&&t._UZ(0,"app-exam-history-item",21),2&n){const e=t.oxw().element;t.Q6J("title","Exam #"+e.id)("date",e.creationDate)("mark",e.mark)("timeSpent",e.timeSpent)("questionList",e.questionList)("answerList",e.answerList)}}function b(n,o){1&n&&t._UZ(0,"app-exam-history-item",22),2&n&&t.Q6J("isOffline",!0)}function A(n,o){if(1&n&&(t.YNc(0,I,1,6,"app-exam-history-item",19),t.YNc(1,b,1,1,"ng-template",null,20,t.W1O)),2&n){const e=o.element,a=t.MAs(2);t.Q6J("ngIf",e)("ngIfElse",a)}}function E(n,o){if(1&n&&(t.TgZ(0,"app-infinite-scroll-list",17),t.YNc(1,A,3,2,"ng-template",null,18,t.W1O),t.qZA()),2&n){const e=t.oxw(3);t.Q6J("elementSize",128)("datasource",e.examItemDatasource)}}function U(n,o){1&n&&(t.TgZ(0,"div",23)(1,"mat-icon"),t._uU(2,"rule"),t.qZA(),t.TgZ(3,"span",24),t._uU(4,"No exams corrected yet"),t.qZA(),t._UZ(5,"br"),t.TgZ(6,"span",25),t._uU(7,"Go to the exam section and complete your first!"),t.qZA()())}function S(n,o){if(1&n&&(t.TgZ(0,"div",14),t.YNc(1,E,3,2,"app-infinite-scroll-list",15),t.YNc(2,U,8,0,"ng-template",null,16,t.W1O),t.qZA()),2&n){const e=o.ngIf,a=t.MAs(3);t.xp6(1),t.Q6J("ngIf",e.length)("ngIfElse",a)}}function w(n,o){if(1&n&&(t.TgZ(0,"div",4)(1,"mat-accordion")(2,"mat-expansion-panel",5)(3,"mat-expansion-panel-header",6)(4,"mat-panel-title")(5,"mat-icon"),t._uU(6,"account_circle"),t.qZA(),t.TgZ(7,"div",7)(8,"span",8),t._uU(9),t.qZA(),t.TgZ(10,"span",9),t._uU(11),t.qZA(),t.TgZ(12,"span",10),t._uU(13),t.qZA()()()(),t.TgZ(14,"div",11)(15,"div",12)(16,"mat-icon",2),t._uU(17,"history"),t.qZA(),t.TgZ(18,"h3"),t._uU(19,"Exam history"),t.qZA()(),t.YNc(20,S,4,2,"div",13),t.ALo(21,"async"),t.qZA()()()()),2&n){const e=o.ngIf,a=t.oxw();let c;t.xp6(2),t.Q6J("disabled",!0)("expanded",!0),t.xp6(7),t.Oqu(e.fullName),t.xp6(2),t.Oqu(e.email),t.xp6(2),t.hij("@",e.username,""),t.xp6(7),t.Q6J("ngIf",null==(c=t.lcZ(21,6,a.hasRepliedExams$))?null:c.content)}}const q=[{path:"",component:(()=>{class n{constructor(e,a){this.examService=e,this.authStore=a,this.page=0}ngOnInit(){this.user$=this.authStore.user$,this.examService.getUserExams(this.user$,this.page,!0).subscribe(),this.hasRepliedExams$=this.examService.getUserExams(this.user$,this.page,!0),this.examItemDatasource=new d(this.examService,this.authStore)}}return n.\u0275fac=function(e){return new(e||n)(t.Y36(u.O),t.Y36(x.C))},n.\u0275cmp=t.Xpm({type:n,selectors:[["app-profile"]],decls:8,vars:3,consts:[["color","primary",1,"tool-bar"],["mat-button","","disabled",""],[1,"icon"],["class","container",4,"ngIf"],[1,"container"],["hideToggle","",3,"disabled","expanded"],["expandedHeight","16rem"],[1,"user-info"],[1,"name"],[1,"email"],[1,"username"],[1,"exam-history"],[1,"title"],["class","history-item-list",4,"ngIf"],[1,"history-item-list"],[3,"elementSize","datasource",4,"ngIf","ngIfElse"],["noExamsYet",""],[3,"elementSize","datasource"],["elementTemplate",""],["class","item-size",3,"title","date","mark","timeSpent","questionList","answerList",4,"ngIf","ngIfElse"],["noExam",""],[1,"item-size",3,"title","date","mark","timeSpent","questionList","answerList"],[1,"item-size",3,"isOffline"],[1,"empty-history"],[1,"first-empty-title"],[1,"second-empty-title"]],template:function(e,a){1&e&&(t.TgZ(0,"mat-toolbar",0)(1,"div",1)(2,"mat-icon",2),t._uU(3,"person"),t.qZA(),t.TgZ(4,"span"),t._uU(5,"Profile"),t.qZA()()(),t.YNc(6,w,22,8,"div",3),t.ALo(7,"async")),2&e&&(t.xp6(6),t.Q6J("ngIf",t.lcZ(7,1,a.user$)))},directives:[P.Ye,O.Hw,r.O5,s.pp,s.ib,s.yz,s.yK,M.G,T],pipes:[r.Ov],styles:[".container[_ngcontent-%COMP%]{margin:3rem 18rem}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]{background-color:#673ab7}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   .mat-expansion-panel-header[aria-disabled=true][_ngcontent-%COMP%]{color:#fff}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   mat-panel-title[_ngcontent-%COMP%]{display:flex;flex-direction:column;align-items:center;justify-content:center}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   mat-panel-title[_ngcontent-%COMP%]   mat-icon[_ngcontent-%COMP%]{width:8rem;height:8rem;font-size:8rem}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   mat-panel-title[_ngcontent-%COMP%]   .user-info[_ngcontent-%COMP%]{display:flex;flex-direction:column;align-items:center}.container[_ngcontent-%COMP%]   mat-expansion-panel[_ngcontent-%COMP%]   mat-panel-title[_ngcontent-%COMP%]   .user-info[_ngcontent-%COMP%]   .name[_ngcontent-%COMP%]{font-weight:500;font-size:1.4rem;margin-bottom:.75rem}.container[_ngcontent-%COMP%]     .mat-expansion-panel-body{background-color:#fff!important}.container[_ngcontent-%COMP%]   .exam-history[_ngcontent-%COMP%]{margin-top:2rem}.container[_ngcontent-%COMP%]   .exam-history[_ngcontent-%COMP%]   .title[_ngcontent-%COMP%]{display:flex;align-items:center}.container[_ngcontent-%COMP%]   .exam-history[_ngcontent-%COMP%]   .title[_ngcontent-%COMP%]   mat-icon[_ngcontent-%COMP%]{width:1.8rem;height:1.8rem;font-size:1.8rem}.container[_ngcontent-%COMP%]   .exam-history[_ngcontent-%COMP%]   .title[_ngcontent-%COMP%]   h3[_ngcontent-%COMP%]{font-size:1.5rem;font-weight:500;margin-left:.3rem;margin-bottom:0}.container[_ngcontent-%COMP%]   .exam-history[_ngcontent-%COMP%]   .history-item-list[_ngcontent-%COMP%]{margin-top:2rem;margin-left:.4rem}.container[_ngcontent-%COMP%]   .exam-history[_ngcontent-%COMP%]   .empty-history[_ngcontent-%COMP%]{display:flex;flex-direction:column;align-items:center;margin-top:4rem;margin-bottom:6rem}.container[_ngcontent-%COMP%]   .exam-history[_ngcontent-%COMP%]   .empty-history[_ngcontent-%COMP%]   mat-icon[_ngcontent-%COMP%]{width:10rem;height:10rem;font-size:10rem;color:#bfbfbf;margin-bottom:1rem}.container[_ngcontent-%COMP%]   .exam-history[_ngcontent-%COMP%]   .empty-history[_ngcontent-%COMP%]   .first-empty-title[_ngcontent-%COMP%]{font-size:1.75rem;color:#bfbfbf;font-weight:500;text-align:center}.container[_ngcontent-%COMP%]   .exam-history[_ngcontent-%COMP%]   .empty-history[_ngcontent-%COMP%]   .second-empty-title[_ngcontent-%COMP%]{font-size:1rem;color:#bfbfbf;font-weight:500;text-align:center}"]}),n})()}];let z=(()=>{class n{}return n.\u0275fac=function(e){return new(e||n)},n.\u0275mod=t.oAB({type:n}),n.\u0275inj=t.cJS({imports:[[f.Bz.forChild(q)],f.Bz]}),n})(),L=(()=>{class n{}return n.\u0275fac=function(e){return new(e||n)},n.\u0275mod=t.oAB({type:n}),n.\u0275inj=t.cJS({imports:[[r.ez,z,g.m,l.q,p]]}),n})()}}]);