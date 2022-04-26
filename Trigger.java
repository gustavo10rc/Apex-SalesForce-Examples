//1 - Realize uma trigger onde ao criar uma oportunidade caso 
//o valor da oportunidade seja >= a 100000 e <= 200000, 
//a fase deve ser colocada automaticamente para value proposition, senão a fase deve ficar em prospecting.

trigger OppTrigger on Opportunity (before Insert) {
    
    for(Opportunity opp : Trigger.New){
        
        if(opp.Amount >= 100000 && opp.Amount <= 200000){
            	opp.StageName = 'value proposition';
        }else{
        		opp.StageName = 'prospecting';
        }
        
    }
}

//2 - Realize uma trigger, que marque novas contas do setor de “Tecnologia” ou “Finanças” como “Hot” 
//no sistema, ajudando assim sua empresa a priorizar essas vendas.

trigger AccountTrigger on Account (before insert) {
    
    for(Account account: Trigger.new){
        
	     if(account.Industry == 'Technology' ||account.Industry == 'Finance' ){
	     	account.Rating = 'Hot';
	     }
    }
}

//3 - Preencha uma descrição do contato quando o usuário criar um contato.
trigger ContactTrigger on Contact (before insert) {

    
    for(Contact cont: Trigger.new){
        
	     if(cont.Description == null){
	     	cont.Description = 'Usuario foi criado e essa é uma breve descrição dele :) ';
	     }
    }
}


//4 - Preencha a descrição do contato com o nome de usuário modificado quando o usuário atualizar o contato.
trigger ContactTrigger on Contact (before update) {

    
    for(Contact cont: Trigger.new){
        
	     	cont.Description = 'Usuario que modificou esse contato foi '+ userInfo.getName();
	     
    }
} 

//5 - Realize a inserção dos cenários dos exercícios 3 e 4  acima em um gatilho.
trigger ContactTrigger on Contact (before insert, before update) {

    
    for(Contact cont: Trigger.new){
        
        if(trigger.isInsert){
           
           if(cont.Description == null){
	     	cont.Description = 'Usuario foi criado e essa é uma breve descrição dele :) ';
	     	}
	     	
       	}

		If(trigger.isUpdate){
    		cont.Description = 'Usuario que modificou esse contato foi '+ userInfo.getName();    
        }
    }
}


//6 - Criar uma trigger em oportunidade para o caso o valor da oportunidade seja maior que 500000, 
//automaticamente a fase passa para negotiation/review.
trigger OppTrigger on Opportunity (before Insert) {
    
    for(Opportunity opp : Trigger.New){
        
        if(opp.Amount > 500000){
            	opp.StageName = 'negotiation/review';
        }
    }
}


//7 - Ao inserir um lead, caso a origem do lead seja web, deve ser classificado como warm. 
//caso seja other deve ser classificado como cold.

trigger LeadTrigger on Lead (before insert) {
    for (Lead ld: Trigger.new){
        
        if(ld.LeadSource == 'Web'){
            ld.Rating = 'Warm';
        }else if(ld.LeadSource == 'Other'){
        	ld.Rating = 'Cold';
        }
        
    }
}

//8. Antes de inserir uma nova conta, faça:

//- Não permitir que a receita anual seja nula ou negativa.
//- Se receita anual < 100000, salvar classificação como cold.
//- Se receita anual >= 100000 e < 500000, salvar classificação como warm.
//- Se receita anual >=500000, classificação hot.

trigger AccountTrigger on Account (before insert) {
    
    for(Account account: Trigger.new){
        
	     if(account.AnnualRevenue == null || account.AnnualRevenue < 0){
	     	account.AnnualRevenue.addError('O campo não deve estar vazio');
	     }

        if(account.AnnualRevenue < 100000)
        {
            account.rating = 'cold';
        }else if(account.AnnualRevenue >= 100000 && account.AnnualRevenue < 500000){
            account.rating = 'Warm';
        }else if(account.AnnualRevenue >= 500000){
            account.rating = 'Hot';
        }
    }
}