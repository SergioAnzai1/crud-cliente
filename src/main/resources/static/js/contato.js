document.addEventListener('DOMContentLoaded', function(){
    const tbodyClientes = document.getElementById('tbodyClientes');
    
    if(tbodyClientes) {
        carregarClientes();
    }
    function excluirCliente(id, nome) {
        if (confirm("Deseja realmente excluir o cliente " + nome + "?")) {
            fetch('/clientes/' + id, {
                method: 'DELETE',
            })
            .then(function(response) {
                if (response.ok) {
                    alert('Cliente excluido com sucesso!');
                    carregarClientes();
                } else {
                    alert('Erro ao excluir cliente.');
                }
            })
            .catch(function(error){
                console.error('Erro: ', error);
                alert('Erro ao excluir cliente. Tente novamente.');
            })
        }
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
                
                const btnEditar = document.createElement('button');
                btnEditar.textContent = 'Editar';
                btnEditar.className = 'btn-editar';
                btnEditar.onclick = function() {
                    abrirModalEditar(cliente);
                };

                const btnExcluir = document.createElement('button');
                btnExcluir.textContent = 'Excluir';
                btnExcluir.className = 'btn-excluir';
                btnExcluir.onclick = function() {
                    excluirCliente(cliente.id, cliente.nome);
                };
                tdAcoes.appendChild(btnEditar);
                tdAcoes.appendChild(btnExcluir);
                
                
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

    // Função para abrir modal de edição
    function abrirModalEditar(cliente) {
        const modal = document.getElementById('modalEditar');
        const form = document.getElementById('formEditarCliente');
        
        // Preencher campos do formulário com os dados do cliente
        document.getElementById('editarId').value = cliente.id;
        document.getElementById('editarNome').value = cliente.nome;
        document.getElementById('editarCpf').value = cliente.cpf;
        
        // Formatar data para o input type="date" (YYYY-MM-DD)
        if (cliente.dataNascimento) {
            let dataFormatada = cliente.dataNascimento;
            // Se a data contém 'T' (formato ISO com hora), remove a parte da hora
            if (dataFormatada.includes('T')) {
                dataFormatada = dataFormatada.split('T')[0];
            }
            // Se a data está no formato DD/MM/YYYY, converte para YYYY-MM-DD
            if (dataFormatada.includes('/')) {
                const partes = dataFormatada.split('/');
                dataFormatada = `${partes[2]}-${partes[1]}-${partes[0]}`;
            }
            document.getElementById('editarDataNascimento').value = dataFormatada;
        } else {
            document.getElementById('editarDataNascimento').value = '';
        }
        
        document.getElementById('editarEndereco').value = cliente.endereco || '';
        
        // Mostrar modal
        modal.style.display = 'block';
    }

    // Função para fechar modal
    function fecharModalEditar() {
        const modal = document.getElementById('modalEditar');
        modal.style.display = 'none';
        document.getElementById('formEditarCliente').reset();
    }

    // Event listeners para o modal
    const modal = document.getElementById('modalEditar');
    const btnCancelar = document.getElementById('btnCancelarEditar');
    const spanClose = document.querySelector('.modal-close');

    if (spanClose) {
        spanClose.onclick = fecharModalEditar;
    }

    if (btnCancelar) {
        btnCancelar.onclick = fecharModalEditar;
    }

    // Fechar modal ao clicar fora dele
    if (modal) {
        window.onclick = function(event) {
            if (event.target === modal) {
                fecharModalEditar();
            }
        };
    }

    // Submeter formulário de edição
    const formEditar = document.getElementById('formEditarCliente');
    if (formEditar) {
        formEditar.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const id = document.getElementById('editarId').value;
            const clienteDTO = {
                id: parseInt(id),
                nome: document.getElementById('editarNome').value,
                cpf: document.getElementById('editarCpf').value,
                dataNascimento: document.getElementById('editarDataNascimento').value,
                endereco: document.getElementById('editarEndereco').value
            };

            fetch('/clientes/' + id, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(clienteDTO)
            })
            .then(function(response) {
                if (response.ok) {
                    alert('Cliente atualizado com sucesso!');
                    fecharModalEditar();
                    carregarClientes();
                } else {
                    return response.json().then(function(data) {
                        let mensagem = 'Erro ao atualizar cliente.';
                        if (data.message) {
                            mensagem = data.message;
                        }
                        alert(mensagem);
                    });
                }
            })
            .catch(function(error) {
                console.error('Erro: ', error);
                alert('Erro ao atualizar cliente. Tente novamente.');
            });
        });
    }
});