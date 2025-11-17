document.addEventListener('DOMContentLoaded', function(){
    const tbodyClientes = document.getElementById('tbodyClientes');
    
    if(tbodyClientes) {
        carregarClientes();
    }
    
    function carregarClientes() {
        fetch('/clientes')
        .then(response => response.json())
        .then(clientes => {
            tbodyClientes.innerHTML = '';
            if (clientes.length === 0) {
                const tr = document.createElement('tr');
                const td = document.createElement('td');
                const isMobile = window.innerWidth <= 768;
                const colspan = isMobile ? 4 : 6;
                td.setAttribute('colspan', colspan);
                td.textContent = 'Nenhum cliente cadastrado';
                td.style.textAlign = 'center';
                td.style.padding = '2rem';
                td.style.color = '#999';
                tr.appendChild(td);
                tbodyClientes.appendChild(tr);
                return;
            }
            clientes.forEach(cliente => {
                const tr = document.createElement('tr');
                const tdId = document.createElement('td');
                tdId.textContent = cliente.id;
                const tdNome = document.createElement('td');
                tdNome.textContent = cliente.nome;
                const tdCpf = document.createElement('td');
                tdCpf.textContent = cliente.cpf;                
                const tdData = document.createElement('td');
                const dataFormatada = cliente.dataNascimento ? 
                cliente.dataNascimento.split('-').reverse().join('/') : '-';
                tdData.textContent = dataFormatada;
                const tdEndereco = document.createElement('td');
                tdEndereco.textContent = cliente.endereco || '-';
                const tdAcoes = document.createElement('td');
                tdAcoes.textContent = 'Editar | Excluir'; // Por enquanto só texto
                
                // Adicionar células ao tr
                tr.appendChild(tdId);
                tr.appendChild(tdNome);
                tr.appendChild(tdCpf);
                tr.appendChild(tdData);
                tr.appendChild(tdEndereco);
                tr.appendChild(tdAcoes);
                
                // Adicionar tr ao tbody
                tbodyClientes.appendChild(tr);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar clientes:', error);
        });
    }
});