"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[452],{9970:(M,g,r)=>{r.d(g,{i:()=>y});var u=r(6704),o=r(5e3),C=r(122),s=r(6688),q=r(9808),p=r(5245),e=r(7423);function b(l,m){1&l&&(o.TgZ(0,"mat-chip",12)(1,"mat-icon"),o._uU(2,"pending"),o.qZA()())}function O(l,m){1&l&&(o.TgZ(0,"mat-chip",13)(1,"mat-icon"),o._uU(2,"done"),o.qZA()())}function f(l,m){1&l&&(o.TgZ(0,"mat-chip",14)(1,"mat-icon"),o._uU(2,"close"),o.qZA()())}let y=(()=>{class l{constructor(_){this.correctionQuestionReplierService=_}correctQuestion(_){this.answer.correctionStatus=_?u.W.Correct:u.W.Incorrect,this.correctionQuestionReplierService.addCorrectedQuestion({question:this.question,answer:this.answer})}}return l.\u0275fac=function(_){return new(_||l)(o.Y36(C.U))},l.\u0275cmp=o.Xpm({type:l,selectors:[["app-correction-question-type0"]],inputs:{question:"question",answer:"answer",questionNumber:"questionNumber",userID:"userID"},decls:19,vars:9,consts:[[1,"question"],[1,"info"],[3,"ngSwitch"],["class","chip","color","accent","selected","",4,"ngSwitchCase"],["class","chip correct","color","primary","selected","",4,"ngSwitchCase"],["class","chip","color","warn","selected","",4,"ngSwitchCase"],["color","primary","selected","",1,"chip"],[1,"question-text"],[1,"answer-text"],[1,"actions"],["data-cy","correct-question-button","mat-raised-button","",3,"click"],["mat-raised-button","","color","warn",3,"click"],["color","accent","selected","",1,"chip"],["color","primary","selected","",1,"chip","correct"],["color","warn","selected","",1,"chip"]],template:function(_,x){1&_&&(o.TgZ(0,"div",0)(1,"mat-chip-list",1),o.ynx(2,2),o.YNc(3,b,3,0,"mat-chip",3),o.YNc(4,O,3,0,"mat-chip",4),o.YNc(5,f,3,0,"mat-chip",5),o.BQk(),o.TgZ(6,"mat-chip",6),o._uU(7),o.qZA()(),o.TgZ(8,"p",7),o._uU(9),o._UZ(10,"br"),o.qZA(),o.TgZ(11,"p",8),o._uU(12),o.ALo(13,"titlecase"),o.qZA(),o.TgZ(14,"div",9)(15,"button",10),o.NdJ("click",function(){return x.correctQuestion(!0)}),o._uU(16,"Correct"),o.qZA(),o.TgZ(17,"button",11),o.NdJ("click",function(){return x.correctQuestion(!1)}),o._uU(18,"Incorrect"),o.qZA()()()),2&_&&(o.xp6(2),o.Q6J("ngSwitch",x.answer.correctionStatus),o.xp6(1),o.Q6J("ngSwitchCase","PENDING"),o.xp6(1),o.Q6J("ngSwitchCase","CORRECT"),o.xp6(1),o.Q6J("ngSwitchCase","INCORRECT"),o.xp6(2),o.hij("Question #",x.questionNumber,""),o.xp6(2),o.hij("\xbfQue es el ",x.question.conceptText,"? "),o.xp6(3),o.Oqu(o.lcZ(13,7,x.answer.reply)))},directives:[s.qn,q.RF,q.n9,s.HS,p.Hw,e.lW],pipes:[q.rS],styles:[".question-reply[_ngcontent-%COMP%]{width:100%}",".question[_ngcontent-%COMP%]{border:1px solid #dbdbdb;border-radius:.25rem;padding:2rem 2rem 1rem;margin:2rem 0}.question[_ngcontent-%COMP%]   .chip[_ngcontent-%COMP%]{border-radius:.5rem;margin-bottom:1rem;font-size:1.25rem;color:#fff}.question[_ngcontent-%COMP%]   .correct[_ngcontent-%COMP%]{background-color:#00bf6c}.question[_ngcontent-%COMP%]   .question-text[_ngcontent-%COMP%]{font-weight:500;margin-left:.25rem}.question[_ngcontent-%COMP%]   .answer-text[_ngcontent-%COMP%]{margin-left:.25rem}.question[_ngcontent-%COMP%]   .actions[_ngcontent-%COMP%]{margin-top:2rem}.question[_ngcontent-%COMP%]   .actions[_ngcontent-%COMP%]   button[_ngcontent-%COMP%]{margin-right:1rem}"]}),l})()},122:(M,g,r)=>{r.d(g,{U:()=>o});var u=r(1135);class o{constructor(){this.correctedQuestionsSubject=new u.X([]),this.numberOfQuestions=0,this.correctedQuestions$=this.correctedQuestionsSubject.asObservable()}addCorrectedQuestion(s){console.log(s);const q=this.correctedQuestionsSubject.getValue(),p=q.findIndex(({question:{id:e}})=>e===s.question.id);if(-1===p)return this.correctedQuestionsSubject.next([...q,s]);q[p]=s,this.correctedQuestionsSubject.next(q)}getCorrectedQuestions(){return this.correctedQuestionsSubject.getValue()}setNumberOfQuestions(s){this.numberOfQuestions=s}}},5310:(M,g,r)=>{r.d(g,{k:()=>f});var u=r(3075),o=r(6704),C=r(9390),s=r(5e3),q=r(2852),p=r(1125),e=r(7322),b=r(8833),O=r(4533);let f=(()=>{class y{constructor(m,_){this.formBuilder=m,this.examQuestionReplierService=_,this.questionReplyForm=new u.cw({})}ngOnInit(){this.questionReplyForm=this.formBuilder.group({text:[null]}),this.examQuestionReplierService.replyQuestions$.subscribe(m=>{m&&this.onQuestionReplied()})}onQuestionReplied(){const m=this.questionReplyForm.getRawValue(),_={question:Object.assign({},this.question),answer:{type:C.e.TYPE0,correctionStatus:o.W.Pending,reply:m.text?m.text:null,userID:this.userID,questionID:this.question.id}};this.examQuestionReplierService.addRepliedQuestion(_)}}return y.\u0275fac=function(m){return new(m||y)(s.Y36(u.qu),s.Y36(q.d))},y.\u0275cmp=s.Xpm({type:y,selectors:[["app-exam-question-type0"]],inputs:{question:"question",questionNumber:"questionNumber",userID:"userID"},decls:13,vars:4,consts:[["hideToggle","",1,"question-panel",3,"expanded"],[1,"question-title"],[1,"question-content"],[1,"form",3,"formGroup"],["appearance","outline",1,"question-reply"],["rows","2","matInput","","formControlName","text","cdkTextareaAutosize","","placeholder","Reply"]],template:function(m,_){1&m&&(s.TgZ(0,"mat-accordion")(1,"mat-expansion-panel",0)(2,"mat-expansion-panel-header")(3,"mat-panel-title",1),s._uU(4),s.qZA()(),s.TgZ(5,"div",2)(6,"p"),s._uU(7),s.qZA(),s.TgZ(8,"form",3)(9,"mat-form-field",4)(10,"mat-label"),s._uU(11,"Write your reply..."),s.qZA(),s._UZ(12,"textarea",5),s.qZA()()()()()),2&m&&(s.xp6(1),s.Q6J("expanded",!0),s.xp6(3),s.hij(" Question ",_.questionNumber,""),s.xp6(3),s.hij("\xbfQue es ",_.question.conceptText," ?"),s.xp6(1),s.Q6J("formGroup",_.questionReplyForm))},directives:[p.pp,p.ib,p.yz,p.yK,u._Y,u.JL,u.sg,e.KE,e.hX,b.Nt,u.Fj,O.IC,u.JJ,u.u],styles:[".question-reply[_ngcontent-%COMP%]{width:100%}",".question-panel[_ngcontent-%COMP%]{background-color:#673ab7}.question-panel[_ngcontent-%COMP%]   .question-title[_ngcontent-%COMP%]{color:#fff}.question-panel[_ngcontent-%COMP%]     .mat-expansion-panel-body{background-color:#fff!important}.question-panel[_ngcontent-%COMP%]   .question-content[_ngcontent-%COMP%]{margin-top:2rem}"]}),y})()},2852:(M,g,r)=>{r.d(g,{d:()=>o});var u=r(1135);class o{constructor(){this.replyQuestionsSubject=new u.X(!1),this.replyQuestions$=this.replyQuestionsSubject.asObservable(),this.examFullyRepliedSubject=new u.X(!1),this.examFullyReplied$=this.examFullyRepliedSubject.asObservable(),this.numberOfQuestions=0,this.examResponses=[]}addRepliedQuestion(s){this.examResponses=[...this.examResponses,s],this.examResponses.length===this.numberOfQuestions&&this.examFullyRepliedSubject.next(!0)}notifyComponentsToSendReplies(){this.replyQuestionsSubject.next(!0)}setNumberOfQuestions(s){this.numberOfQuestions=s}}},452:(M,g,r)=>{r.d(g,{_:()=>ee});var u=r(9970),o=r(5310),C=r(9390),p=r(6704),e=r(5e3),b=r(122),O=r(6688),f=r(9808),y=r(5245),l=r(7423);function m(t,c){1&t&&(e.TgZ(0,"mat-chip",12)(1,"mat-icon"),e._uU(2,"pending"),e.qZA()())}function _(t,c){1&t&&(e.TgZ(0,"mat-chip",13)(1,"mat-icon"),e._uU(2,"done"),e.qZA()())}function x(t,c){1&t&&(e.TgZ(0,"mat-chip",14)(1,"mat-icon"),e._uU(2,"close"),e.qZA()())}let R=(()=>{class t{constructor(n){this.correctionQuestionReplierService=n}correctQuestion(n){this.answer.correctionStatus=n?p.W.Correct:p.W.Incorrect,this.correctionQuestionReplierService.addCorrectedQuestion({question:this.question,answer:this.answer})}}return t.\u0275fac=function(n){return new(n||t)(e.Y36(b.U))},t.\u0275cmp=e.Xpm({type:t,selectors:[["app-correction-question-type1"]],inputs:{question:"question",answer:"answer",questionNumber:"questionNumber",userID:"userID"},decls:18,vars:10,consts:[[1,"question"],[1,"info"],[3,"ngSwitch"],["class","chip","color","accent","selected","",4,"ngSwitchCase"],["class","chip correct","color","primary","selected","",4,"ngSwitchCase"],["class","chip","color","warn","selected","",4,"ngSwitchCase"],["color","primary","selected","",1,"chip"],[1,"question-text"],[1,"answer-text"],[1,"actions"],["data-cy","correct-question-button","mat-raised-button","",3,"click"],["mat-raised-button","","color","warn",3,"click"],["color","accent","selected","",1,"chip"],["color","primary","selected","",1,"chip","correct"],["color","warn","selected","",1,"chip"]],template:function(n,i){1&n&&(e.TgZ(0,"div",0)(1,"mat-chip-list",1),e.ynx(2,2),e.YNc(3,m,3,0,"mat-chip",3),e.YNc(4,_,3,0,"mat-chip",4),e.YNc(5,x,3,0,"mat-chip",5),e.BQk(),e.TgZ(6,"mat-chip",6),e._uU(7),e.qZA()(),e.TgZ(8,"p",7),e._uU(9),e.qZA(),e.TgZ(10,"p",8),e._uU(11),e.ALo(12,"titlecase"),e.qZA(),e.TgZ(13,"div",9)(14,"button",10),e.NdJ("click",function(){return i.correctQuestion(!0)}),e._uU(15,"Correct"),e.qZA(),e.TgZ(16,"button",11),e.NdJ("click",function(){return i.correctQuestion(!1)}),e._uU(17,"Incorrect"),e.qZA()()()),2&n&&(e.xp6(2),e.Q6J("ngSwitch",i.answer.correctionStatus),e.xp6(1),e.Q6J("ngSwitchCase","PENDING"),e.xp6(1),e.Q6J("ngSwitchCase","CORRECT"),e.xp6(1),e.Q6J("ngSwitchCase","INCORRECT"),e.xp6(2),e.hij("Question #",i.questionNumber,""),e.xp6(2),e.AsE(" \xbfPor qu\xe9 no es correcto afirmar que ",i.question.conceptText," es ",i.question.incorrectDefinitionText,"? "),e.xp6(2),e.Oqu(e.lcZ(12,8,i.answer.reply)))},directives:[O.qn,f.RF,f.n9,O.HS,y.Hw,l.lW],pipes:[f.rS],styles:[".question-reply[_ngcontent-%COMP%]{width:100%}",".question[_ngcontent-%COMP%]{border:1px solid #dbdbdb;border-radius:.25rem;padding:2rem 2rem 1rem;margin:2rem 0}.question[_ngcontent-%COMP%]   .chip[_ngcontent-%COMP%]{border-radius:.5rem;margin-bottom:1rem;font-size:1.25rem;color:#fff}.question[_ngcontent-%COMP%]   .correct[_ngcontent-%COMP%]{background-color:#00bf6c}.question[_ngcontent-%COMP%]   .question-text[_ngcontent-%COMP%]{font-weight:500;margin-left:.25rem}.question[_ngcontent-%COMP%]   .answer-text[_ngcontent-%COMP%]{margin-left:.25rem}.question[_ngcontent-%COMP%]   .actions[_ngcontent-%COMP%]{margin-top:2rem}.question[_ngcontent-%COMP%]   .actions[_ngcontent-%COMP%]   button[_ngcontent-%COMP%]{margin-right:1rem}"]}),t})();var a=r(3075),w=r(2852),h=r(1125),Z=r(7322),S=r(8833),D=r(4533);let I=(()=>{class t{constructor(n,i){this.formBuilder=n,this.examQuestionReplierService=i,this.questionReplyForm=new a.cw({})}ngOnInit(){this.questionReplyForm=this.formBuilder.group({text:[null]}),this.examQuestionReplierService.replyQuestions$.subscribe(n=>{n&&this.onQuestionReplied()})}onQuestionReplied(){const n=this.questionReplyForm.value,i={question:Object.assign({},this.question),answer:{type:C.e.TYPE1,correctionStatus:p.W.Pending,reply:n.text?n.text:null,userID:this.userID,questionID:this.question.id}};this.examQuestionReplierService.addRepliedQuestion(i)}}return t.\u0275fac=function(n){return new(n||t)(e.Y36(a.qu),e.Y36(w.d))},t.\u0275cmp=e.Xpm({type:t,selectors:[["app-exam-question-type1"]],inputs:{question:"question",questionNumber:"questionNumber",userID:"userID"},decls:13,vars:5,consts:[["hideToggle","",1,"question-panel",3,"expanded"],[1,"question-title"],[1,"question-content"],[1,"form",3,"formGroup"],["appearance","outline",1,"question-reply"],["rows","2","matInput","","formControlName","text","cdkTextareaAutosize","","placeholder","Reply"]],template:function(n,i){1&n&&(e.TgZ(0,"mat-accordion")(1,"mat-expansion-panel",0)(2,"mat-expansion-panel-header")(3,"mat-panel-title",1),e._uU(4),e.qZA()(),e.TgZ(5,"div",2)(6,"p"),e._uU(7),e.qZA(),e.TgZ(8,"form",3)(9,"mat-form-field",4)(10,"mat-label"),e._uU(11,"Write your reply..."),e.qZA(),e._UZ(12,"textarea",5),e.qZA()()()()()),2&n&&(e.xp6(1),e.Q6J("expanded",!0),e.xp6(3),e.hij(" Question ",i.questionNumber,""),e.xp6(3),e.AsE("\xbfPor qu\xe9 no es correcto afirmar que ",i.question.conceptText," es ",i.question.incorrectDefinitionText,"?"),e.xp6(1),e.Q6J("formGroup",i.questionReplyForm))},directives:[h.pp,h.ib,h.yz,h.yK,a._Y,a.JL,a.sg,Z.KE,Z.hX,S.Nt,a.Fj,D.IC,a.JJ,a.u],styles:[".question-reply[_ngcontent-%COMP%]{width:100%}",".question-panel[_ngcontent-%COMP%]{background-color:#673ab7}.question-panel[_ngcontent-%COMP%]   .question-title[_ngcontent-%COMP%]{color:#fff}.question-panel[_ngcontent-%COMP%]     .mat-expansion-panel-body{background-color:#fff!important}.question-panel[_ngcontent-%COMP%]   .question-content[_ngcontent-%COMP%]{margin-top:2rem}"]}),t})();function A(t,c){1&t&&(e.TgZ(0,"mat-chip",12)(1,"mat-icon"),e._uU(2,"pending"),e.qZA()())}function Y(t,c){1&t&&(e.TgZ(0,"mat-chip",13)(1,"mat-icon"),e._uU(2,"done"),e.qZA()())}function F(t,c){1&t&&(e.TgZ(0,"mat-chip",14)(1,"mat-icon"),e._uU(2,"close"),e.qZA()())}let J=(()=>{class t{constructor(n){this.correctionQuestionReplierService=n}correctQuestion(n){this.answer.correctionStatus=n?p.W.Correct:p.W.Incorrect,this.correctionQuestionReplierService.addCorrectedQuestion({question:this.question,answer:this.answer})}}return t.\u0275fac=function(n){return new(n||t)(e.Y36(b.U))},t.\u0275cmp=e.Xpm({type:t,selectors:[["app-correction-question-type2"]],inputs:{question:"question",answer:"answer",questionNumber:"questionNumber",userID:"userID"},decls:17,vars:8,consts:[[1,"question"],[1,"info"],[3,"ngSwitch"],["class","chip","color","accent","selected","",4,"ngSwitchCase"],["class","chip correct","color","primary","selected","",4,"ngSwitchCase"],["class","chip","color","warn","selected","",4,"ngSwitchCase"],["color","primary","selected","",1,"chip"],[1,"question-text"],[1,"answer-text"],[1,"actions"],["data-cy","correct-question-button","mat-raised-button","",3,"click"],["mat-raised-button","","color","warn",3,"click"],["color","accent","selected","",1,"chip"],["color","primary","selected","",1,"chip","correct"],["color","warn","selected","",1,"chip"]],template:function(n,i){1&n&&(e.TgZ(0,"div",0)(1,"mat-chip-list",1),e.ynx(2,2),e.YNc(3,A,3,0,"mat-chip",3),e.YNc(4,Y,3,0,"mat-chip",4),e.YNc(5,F,3,0,"mat-chip",5),e.BQk(),e.TgZ(6,"mat-chip",6),e._uU(7),e.qZA()(),e.TgZ(8,"p",7),e._uU(9),e.qZA(),e.TgZ(10,"p",8),e._uU(11),e.qZA(),e.TgZ(12,"div",9)(13,"button",10),e.NdJ("click",function(){return i.correctQuestion(!0)}),e._uU(14,"Correct"),e.qZA(),e.TgZ(15,"button",11),e.NdJ("click",function(){return i.correctQuestion(!1)}),e._uU(16,"Incorrect"),e.qZA()()()),2&n&&(e.xp6(2),e.Q6J("ngSwitch",i.answer.correctionStatus),e.xp6(1),e.Q6J("ngSwitchCase","PENDING"),e.xp6(1),e.Q6J("ngSwitchCase","CORRECT"),e.xp6(1),e.Q6J("ngSwitchCase","INCORRECT"),e.xp6(2),e.hij("Question #",i.questionNumber,""),e.xp6(2),e.AsE("\xbfEs correcto afirmar que ",i.question.conceptText," es ",i.question.definitionText,"?"),e.xp6(2),e.Oqu(i.answer.reply?"Yes":"No"))},directives:[O.qn,f.RF,f.n9,O.HS,y.Hw,l.lW],styles:[".question-reply[_ngcontent-%COMP%]{display:flex;flex-direction:column;margin-top:1rem}.question-reply[_ngcontent-%COMP%]   .radio-button[_ngcontent-%COMP%]{margin-bottom:1rem}",".question[_ngcontent-%COMP%]{border:1px solid #dbdbdb;border-radius:.25rem;padding:2rem 2rem 1rem;margin:2rem 0}.question[_ngcontent-%COMP%]   .chip[_ngcontent-%COMP%]{border-radius:.5rem;margin-bottom:1rem;font-size:1.25rem;color:#fff}.question[_ngcontent-%COMP%]   .correct[_ngcontent-%COMP%]{background-color:#00bf6c}.question[_ngcontent-%COMP%]   .question-text[_ngcontent-%COMP%]{font-weight:500;margin-left:.25rem}.question[_ngcontent-%COMP%]   .answer-text[_ngcontent-%COMP%]{margin-left:.25rem}.question[_ngcontent-%COMP%]   .actions[_ngcontent-%COMP%]{margin-top:2rem}.question[_ngcontent-%COMP%]   .actions[_ngcontent-%COMP%]   button[_ngcontent-%COMP%]{margin-right:1rem}"]}),t})();var E=r(9814);let j=(()=>{class t{constructor(n,i){this.formBuilder=n,this.examQuestionReplierService=i,this.questionReplyForm=new a.cw({})}ngOnInit(){this.questionReplyForm=this.formBuilder.group({bool:[null]}),this.examQuestionReplierService.replyQuestions$.subscribe(n=>{n&&this.onQuestionReplied()})}onQuestionReplied(){const n=this.questionReplyForm.value,i={question:Object.assign({},this.question),answer:{type:C.e.TYPE2,correctionStatus:p.W.Pending,reply:n.bool?"0"===n.bool:null,userID:this.userID,questionID:this.question.id}};this.examQuestionReplierService.addRepliedQuestion(i)}}return t.\u0275fac=function(n){return new(n||t)(e.Y36(a.qu),e.Y36(w.d))},t.\u0275cmp=e.Xpm({type:t,selectors:[["app-exam-question-type2"]],inputs:{question:"question",questionNumber:"questionNumber",userID:"userID"},decls:14,vars:5,consts:[["hideToggle","",1,"question-panel",3,"expanded"],[1,"question-title"],[1,"question-content"],[1,"form",3,"formGroup"],["formControlName","bool","aria-label","Select an option",1,"question-reply"],["value","0",1,"radio-button"],["value","1",1,"radio-button"]],template:function(n,i){1&n&&(e.TgZ(0,"mat-accordion")(1,"mat-expansion-panel",0)(2,"mat-expansion-panel-header")(3,"mat-panel-title",1),e._uU(4),e.qZA()(),e.TgZ(5,"div",2)(6,"p"),e._uU(7),e.qZA(),e.TgZ(8,"form",3)(9,"mat-radio-group",4)(10,"mat-radio-button",5),e._uU(11,"Yes"),e.qZA(),e.TgZ(12,"mat-radio-button",6),e._uU(13,"No"),e.qZA()()()()()()),2&n&&(e.xp6(1),e.Q6J("expanded",!0),e.xp6(3),e.hij(" Question ",i.questionNumber,""),e.xp6(3),e.AsE("\xbfEs correcto afirmar que ",i.question.conceptText," es ",i.question.definitionText,"?"),e.xp6(1),e.Q6J("formGroup",i.questionReplyForm))},directives:[h.pp,h.ib,h.yz,h.yK,a._Y,a.JL,a.sg,E.VQ,a.JJ,a.u,E.U0],styles:[".question-reply[_ngcontent-%COMP%]{display:flex;flex-direction:column;margin-top:1rem}.question-reply[_ngcontent-%COMP%]   .radio-button[_ngcontent-%COMP%]{margin-bottom:1rem}",".question-panel[_ngcontent-%COMP%]{background-color:#673ab7}.question-panel[_ngcontent-%COMP%]   .question-title[_ngcontent-%COMP%]{color:#fff}.question-panel[_ngcontent-%COMP%]     .mat-expansion-panel-body{background-color:#fff!important}.question-panel[_ngcontent-%COMP%]   .question-content[_ngcontent-%COMP%]{margin-top:2rem}"]}),t})();function L(t,c){1&t&&(e.TgZ(0,"mat-chip",12)(1,"mat-icon"),e._uU(2,"pending"),e.qZA()())}function K(t,c){1&t&&(e.TgZ(0,"mat-chip",13)(1,"mat-icon"),e._uU(2,"done"),e.qZA()())}function k(t,c){1&t&&(e.TgZ(0,"mat-chip",14)(1,"mat-icon"),e._uU(2,"close"),e.qZA()())}let G=(()=>{class t{constructor(n){this.correctionQuestionReplierService=n}correctQuestion(n){this.answer.correctionStatus=n?p.W.Correct:p.W.Incorrect,this.correctionQuestionReplierService.addCorrectedQuestion({question:this.question,answer:this.answer})}}return t.\u0275fac=function(n){return new(n||t)(e.Y36(b.U))},t.\u0275cmp=e.Xpm({type:t,selectors:[["app-correction-question-type3"]],inputs:{question:"question",answer:"answer",questionNumber:"questionNumber",userID:"userID"},decls:17,vars:9,consts:[[1,"question"],[1,"info"],[3,"ngSwitch"],["class","chip","color","accent","selected","",4,"ngSwitchCase"],["class","chip correct","color","primary","selected","",4,"ngSwitchCase"],["class","chip","color","warn","selected","",4,"ngSwitchCase"],["color","primary","selected","",1,"chip"],[1,"question-text"],[1,"answer-text"],[1,"actions"],["data-cy","correct-question-button","mat-raised-button","",3,"click"],["mat-raised-button","","color","warn",3,"click"],["color","accent","selected","",1,"chip"],["color","primary","selected","",1,"chip","correct"],["color","warn","selected","",1,"chip"]],template:function(n,i){1&n&&(e.TgZ(0,"div",0)(1,"mat-chip-list",1),e.ynx(2,2),e.YNc(3,L,3,0,"mat-chip",3),e.YNc(4,K,3,0,"mat-chip",4),e.YNc(5,k,3,0,"mat-chip",5),e.BQk(),e.TgZ(6,"mat-chip",6),e._uU(7),e.qZA()(),e.TgZ(8,"p",7),e._uU(9),e.qZA(),e.TgZ(10,"p",8),e._uU(11),e.qZA(),e.TgZ(12,"div",9)(13,"button",10),e.NdJ("click",function(){return i.correctQuestion(!0)}),e._uU(14,"Correct"),e.qZA(),e.TgZ(15,"button",11),e.NdJ("click",function(){return i.correctQuestion(!1)}),e._uU(16,"Incorrect"),e.qZA()()()),2&n&&(e.xp6(2),e.Q6J("ngSwitch",i.answer.correctionStatus),e.xp6(1),e.Q6J("ngSwitchCase","PENDING"),e.xp6(1),e.Q6J("ngSwitchCase","CORRECT"),e.xp6(1),e.Q6J("ngSwitchCase","INCORRECT"),e.xp6(2),e.hij("Question #",i.questionNumber,""),e.xp6(2),e.lnq(" \xbfEs cierto que ",i.question.conceptText," no es ",i.question.incorrectDefinitionText," porque ",i.question.justificationText,"? "),e.xp6(2),e.Oqu(i.answer.reply?"Yes":"No"))},directives:[O.qn,f.RF,f.n9,O.HS,y.Hw,l.lW],styles:[".question-reply[_ngcontent-%COMP%]{display:flex;flex-direction:column;margin-top:1rem}.question-reply[_ngcontent-%COMP%]   .radio-button[_ngcontent-%COMP%]{margin-bottom:1rem}",".question[_ngcontent-%COMP%]{border:1px solid #dbdbdb;border-radius:.25rem;padding:2rem 2rem 1rem;margin:2rem 0}.question[_ngcontent-%COMP%]   .chip[_ngcontent-%COMP%]{border-radius:.5rem;margin-bottom:1rem;font-size:1.25rem;color:#fff}.question[_ngcontent-%COMP%]   .correct[_ngcontent-%COMP%]{background-color:#00bf6c}.question[_ngcontent-%COMP%]   .question-text[_ngcontent-%COMP%]{font-weight:500;margin-left:.25rem}.question[_ngcontent-%COMP%]   .answer-text[_ngcontent-%COMP%]{margin-left:.25rem}.question[_ngcontent-%COMP%]   .actions[_ngcontent-%COMP%]{margin-top:2rem}.question[_ngcontent-%COMP%]   .actions[_ngcontent-%COMP%]   button[_ngcontent-%COMP%]{margin-right:1rem}"]}),t})(),X=(()=>{class t{constructor(n,i){this.formBuilder=n,this.examQuestionReplierService=i,this.questionReplyForm=new a.cw({})}ngOnInit(){this.questionReplyForm=this.formBuilder.group({bool:[null]}),this.examQuestionReplierService.replyQuestions$.subscribe(n=>{n&&this.onQuestionReplied()})}onQuestionReplied(){const n=this.questionReplyForm.value,i={question:Object.assign({},this.question),answer:{type:C.e.TYPE3,correctionStatus:p.W.Pending,reply:n.bool?"0"===n.bool:null,userID:this.userID,questionID:this.question.id}};this.examQuestionReplierService.addRepliedQuestion(i)}}return t.\u0275fac=function(n){return new(n||t)(e.Y36(a.qu),e.Y36(w.d))},t.\u0275cmp=e.Xpm({type:t,selectors:[["app-exam-question-type3"]],inputs:{question:"question",questionNumber:"questionNumber",userID:"userID"},decls:14,vars:6,consts:[["hideToggle","",1,"question-panel",3,"expanded"],[1,"question-title"],[1,"question-content"],[1,"form",3,"formGroup"],["formControlName","bool","aria-label","Select an option",1,"question-reply"],["value","1",1,"radio-button"],["value","0",1,"radio-button"]],template:function(n,i){1&n&&(e.TgZ(0,"mat-accordion")(1,"mat-expansion-panel",0)(2,"mat-expansion-panel-header")(3,"mat-panel-title",1),e._uU(4),e.qZA()(),e.TgZ(5,"div",2)(6,"p"),e._uU(7),e.qZA(),e.TgZ(8,"form",3)(9,"mat-radio-group",4)(10,"mat-radio-button",5),e._uU(11,"Yes"),e.qZA(),e.TgZ(12,"mat-radio-button",6),e._uU(13,"No"),e.qZA()()()()()()),2&n&&(e.xp6(1),e.Q6J("expanded",!0),e.xp6(3),e.hij(" Question ",i.questionNumber,""),e.xp6(3),e.lnq(" \xbfEs cierto que ",i.question.conceptText," no es ",i.question.incorrectDefinitionText," porque ",i.question.justificationText,"? "),e.xp6(1),e.Q6J("formGroup",i.questionReplyForm))},directives:[h.pp,h.ib,h.yz,h.yK,a._Y,a.JL,a.sg,E.VQ,a.JJ,a.u,E.U0],styles:[".question-reply[_ngcontent-%COMP%]{display:flex;flex-direction:column;margin-top:1rem}.question-reply[_ngcontent-%COMP%]   .radio-button[_ngcontent-%COMP%]{margin-bottom:1rem}",".question-panel[_ngcontent-%COMP%]{background-color:#673ab7}.question-panel[_ngcontent-%COMP%]   .question-title[_ngcontent-%COMP%]{color:#fff}.question-panel[_ngcontent-%COMP%]     .mat-expansion-panel-body{background-color:#fff!important}.question-panel[_ngcontent-%COMP%]   .question-content[_ngcontent-%COMP%]{margin-top:2rem}"]}),t})();const H={TYPE0:t=>(t=>{const{id:c,conceptID:n,conceptText:i,answerT0:d}=t;return{id:c,type:C.e.TYPE0,conceptID:n,examComponentType:o.k,correctionComponentType:u.i,conceptText:i,answer:d}})(t),TYPE1:t=>(t=>{const{id:c,conceptID:n,definitionID:i,conceptText:d,incorrectDefinitionText:Q,answerT1:T}=t;return{id:c,type:C.e.TYPE1,conceptID:n,definitionID:i,examComponentType:I,correctionComponentType:R,conceptText:d,incorrectDefinitionText:Q,answer:T}})(t),TYPE2:t=>(t=>{const{id:c,conceptID:n,definitionID:i,conceptText:d,definitionText:Q,answerT2:T}=t;return{id:c,type:C.e.TYPE2,conceptID:n,definitionID:i,examComponentType:j,correctionComponentType:J,conceptText:d,definitionText:Q,answer:T}})(t),TYPE3:t=>(t=>{const{id:c,conceptID:n,definitionID:i,justificationID:d,conceptText:Q,incorrectDefinitionText:T,justificationText:P,answerT3:v}=t;return{id:c,type:C.e.TYPE3,conceptID:n,definitionID:i,justificationID:d,examComponentType:X,correctionComponentType:G,conceptText:Q,incorrectDefinitionText:T,justificationText:P,answer:v}})(t)},$={0:t=>t,1:t=>t,2:t=>t,3:t=>t};let ee=(()=>{class t{constructor(){}mapDTOToExam(n){const{questionList:i,answerList:d}=n,Q=i.map(P=>{const{type:v}=P;return H[v](P)}),T=d?d.map(P=>Object.assign({},P)):[];return Object.assign(Object.assign({},n),{questionList:Q,answerList:T})}mapExamToDTO(n,i){const d=i.map(({question:T})=>{const{type:P}=T;return $[P](T)}),Q=i.map(({answer:T})=>Object.assign({},T));return Object.assign(Object.assign({},n),{questionDTOList:d,answerDTOList:Q})}}return t.\u0275fac=function(n){return new(n||t)},t.\u0275prov=e.Yz7({token:t,factory:t.\u0275fac,providedIn:"root"}),t})()},6704:(M,g,r)=>{r.d(g,{W:()=>u});var u=(()=>{return(o=u||(u={})).Correct="CORRECT",o.Incorrect="INCORRECT",o.Pending="PENDING",u;var o})()},9390:(M,g,r)=>{r.d(g,{e:()=>u});var u=(()=>{return(o=u||(u={}))[o.TYPE0=0]="TYPE0",o[o.TYPE1=1]="TYPE1",o[o.TYPE2=2]="TYPE2",o[o.TYPE3=3]="TYPE3",u;var o})()}}]);