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
                tdAcoes.className = 'td-acoes';
                
                // Container para organizar os botões em 2 linhas
                const containerBotoes = document.createElement('div');
                containerBotoes.className = 'botoes-container';
                
                // Primeira linha: Adicionar Contato e Ver Contatos
                const btnAdicionarContato = document.createElement('button');
                btnAdicionarContato.textContent = '➕ Contato';
                btnAdicionarContato.className = 'btn-contato';
                btnAdicionarContato.onclick = function() {
                    abrirModalAdicionarContato(cliente);
                };

                const btnVerContatos = document.createElement('button');
                btnVerContatos.textContent = '👁️ Contatos';
                btnVerContatos.className = 'btn-ver-contatos';
                btnVerContatos.onclick = function() {
                    abrirModalVerContatos(cliente);
                };

                // Segunda linha: Editar e Excluir
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
                
                containerBotoes.appendChild(btnAdicionarContato);
                containerBotoes.appendChild(btnVerContatos);
                containerBotoes.appendChild(btnEditar);
                containerBotoes.appendChild(btnExcluir);
                
                tdAcoes.appendChild(containerBotoes);
                
                
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
        window.addEventListener('click', function(event) {
            if (event.target === modal) {
                fecharModalEditar();
            }
        });
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

    // Função para abrir modal de adicionar contato
    function abrirModalAdicionarContato(cliente) {
        const modal = document.getElementById('modalAdicionarContato');
        
        // Preencher o ID do cliente no formulário
        document.getElementById('contatoClienteId').value = cliente.id;
        document.getElementById('contatoClienteNome').textContent = cliente.nome;
        
        // Limpar campos do formulário
        document.getElementById('formAdicionarContato').reset();
        document.getElementById('contatoClienteId').value = cliente.id; // Restaurar após reset
        
        // Mostrar modal
        modal.style.display = 'block';
    }

    // Função para fechar modal de adicionar contato
    function fecharModalAdicionarContato() {
        const modal = document.getElementById('modalAdicionarContato');
        modal.style.display = 'none';
        document.getElementById('formAdicionarContato').reset();
    }

    // Event listeners para o modal de adicionar contato
    const modalAdicionarContato = document.getElementById('modalAdicionarContato');
    const btnCancelarContato = document.getElementById('btnCancelarContato');
    const spanCloseContato = document.querySelector('.modal-close-contato');

    if (spanCloseContato) {
        spanCloseContato.onclick = fecharModalAdicionarContato;
    }

    if (btnCancelarContato) {
        btnCancelarContato.onclick = fecharModalAdicionarContato;
    }

    // Fechar modal ao clicar fora dele
    if (modalAdicionarContato) {
        window.addEventListener('click', function(event) {
            if (event.target === modalAdicionarContato) {
                fecharModalAdicionarContato();
            }
        });
    }

    // Submeter formulário de adicionar contato
    const formAdicionarContato = document.getElementById('formAdicionarContato');
    if (formAdicionarContato) {
        formAdicionarContato.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const contatoDTO = {
                tipoContato: document.getElementById('contatoTipo').value,
                valorContato: document.getElementById('contatoValor').value,
                observacao: document.getElementById('contatoObservacao').value || null,
                clienteId: parseInt(document.getElementById('contatoClienteId').value)
            };

            fetch('/contatos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(contatoDTO)
            })
            .then(function(response) {
                if (response.ok) {
                    alert('Contato adicionado com sucesso!');
                    fecharModalAdicionarContato();
                    // Recarregar contatos se o modal de visualização estiver aberto
                    const modalVerContatos = document.getElementById('modalVerContatos');
                    if (modalVerContatos && modalVerContatos.style.display === 'block') {
                        const clienteId = parseInt(document.getElementById('contatoClienteId').value);
                        const clienteNome = document.getElementById('clienteNomeHeader').textContent;
                        const cliente = { id: clienteId, nome: clienteNome };
                        abrirModalVerContatos(cliente);
                    }
                } else {
                    return response.json().then(function(data) {
                        let mensagem = 'Erro ao adicionar contato.';
                        if (data.message) {
                            mensagem = data.message;
                        }
                        alert(mensagem);
                    });
                }
            })
            .catch(function(error) {
                console.error('Erro: ', error);
                alert('Erro ao adicionar contato. Tente novamente.');
            });
        });
    }

    // Função para abrir modal de visualizar contatos
    function abrirModalVerContatos(cliente) {
        const modal = document.getElementById('modalVerContatos');
        const tbodyContatos = document.getElementById('tbodyContatos');
        const clienteNomeHeader = document.getElementById('clienteNomeHeader');
        
        // Preencher nome do cliente no cabeçalho
        clienteNomeHeader.textContent = cliente.nome;
        
        // Limpar tabela
        tbodyContatos.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 2rem; color: #999;">Carregando...</td></tr>';
        
        // Mostrar modal
        modal.style.display = 'block';
        
        // Buscar contatos do cliente
        fetch('/contatos/cliente/' + cliente.id)
            .then(response => response.json())
            .then(contatos => {
                tbodyContatos.innerHTML = '';
                
                if (contatos.length === 0) {
                    const tr = document.createElement('tr');
                    const td = document.createElement('td');
                    td.setAttribute('colspan', '4');
                    td.textContent = 'Nenhum contato cadastrado para este cliente';
                    td.style.textAlign = 'center';
                    td.style.padding = '2rem';
                    td.style.color = '#999';
                    tr.appendChild(td);
                    tbodyContatos.appendChild(tr);
                    return;
                }
                
                contatos.forEach(contato => {
                    const tr = document.createElement('tr');
                    
                    const tdTipo = document.createElement('td');
                    tdTipo.textContent = contato.tipoContato === 'TELEFONE' ? '📞 Telefone' : '📧 E-mail';
                    
                    const tdValor = document.createElement('td');
                    tdValor.textContent = contato.valorContato;
                    
                    const tdObservacao = document.createElement('td');
                    tdObservacao.textContent = contato.observacao || '-';
                    
                    const tdAcoes = document.createElement('td');
                    tdAcoes.style.textAlign = 'center';
                    
                    const btnEditarContato = document.createElement('button');
                    btnEditarContato.textContent = 'Editar';
                    btnEditarContato.className = 'btn-editar-contato';
                    btnEditarContato.onclick = function() {
                        abrirModalEditarContato(contato, cliente);
                    };
                    
                    const btnExcluirContato = document.createElement('button');
                    btnExcluirContato.textContent = 'Excluir';
                    btnExcluirContato.className = 'btn-excluir-contato';
                    btnExcluirContato.onclick = function() {
                        excluirContato(contato.id, contato.valorContato, cliente.id);
                    };
                    
                    tdAcoes.appendChild(btnEditarContato);
                    tdAcoes.appendChild(btnExcluirContato);
                    
                    tr.appendChild(tdTipo);
                    tr.appendChild(tdValor);
                    tr.appendChild(tdObservacao);
                    tr.appendChild(tdAcoes);
                    
                    tbodyContatos.appendChild(tr);
                });
            })
            .catch(error => {
                console.error('Erro ao carregar contatos:', error);
                tbodyContatos.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 2rem; color: #f44336;">Erro ao carregar contatos. Tente novamente.</td></tr>';
            });
    }

    // Função para fechar modal de visualizar contatos
    function fecharModalVerContatos() {
        const modal = document.getElementById('modalVerContatos');
        modal.style.display = 'none';
    }

    // Função para abrir modal de editar contato
    function abrirModalEditarContato(contato, cliente) {
        const modal = document.getElementById('modalEditarContato');
        
        // Preencher campos do formulário com os dados do contato
        document.getElementById('editarContatoId').value = contato.id;
        document.getElementById('editarContatoClienteId').value = cliente.id;
        document.getElementById('editarContatoClienteNome').textContent = cliente.nome;
        document.getElementById('editarContatoTipo').value = contato.tipoContato;
        document.getElementById('editarContatoValor').value = contato.valorContato;
        document.getElementById('editarContatoObservacao').value = contato.observacao || '';
        
        // Mostrar modal
        modal.style.display = 'block';
    }

    // Função para fechar modal de editar contato
    function fecharModalEditarContato() {
        const modal = document.getElementById('modalEditarContato');
        modal.style.display = 'none';
        document.getElementById('formEditarContato').reset();
    }

    // Função para excluir contato
    function excluirContato(contatoId, valorContato, clienteId) {
        if (confirm("Deseja realmente excluir o contato " + valorContato + "?")) {
            fetch('/contatos/' + contatoId, {
                method: 'DELETE'
            })
            .then(function(response) {
                if (response.ok) {
                    alert('Contato excluído com sucesso!');
                    // Recarregar contatos no modal preservando o nome do cliente
                    const clienteNome = document.getElementById('clienteNomeHeader').textContent;
                    const cliente = { id: clienteId, nome: clienteNome };
                    abrirModalVerContatos(cliente);
                } else {
                    alert('Erro ao excluir contato.');
                }
            })
            .catch(function(error) {
                console.error('Erro: ', error);
                alert('Erro ao excluir contato. Tente novamente.');
            });
        }
    }

    // Event listeners para o modal de visualizar contatos
    const modalVerContatos = document.getElementById('modalVerContatos');
    const btnFecharContatos = document.getElementById('btnFecharContatos');
    const spanCloseVerContatos = document.querySelector('.modal-close-ver-contatos');

    if (spanCloseVerContatos) {
        spanCloseVerContatos.onclick = fecharModalVerContatos;
    }

    if (btnFecharContatos) {
        btnFecharContatos.onclick = fecharModalVerContatos;
    }

    // Fechar modal ao clicar fora dele
    if (modalVerContatos) {
        window.addEventListener('click', function(event) {
            if (event.target === modalVerContatos) {
                fecharModalVerContatos();
            }
        });
    }

    // Event listeners para o modal de editar contato
    const modalEditarContato = document.getElementById('modalEditarContato');
    const btnCancelarEditarContato = document.getElementById('btnCancelarEditarContato');
    const spanCloseEditarContato = document.querySelector('.modal-close-editar-contato');

    if (spanCloseEditarContato) {
        spanCloseEditarContato.onclick = fecharModalEditarContato;
    }

    if (btnCancelarEditarContato) {
        btnCancelarEditarContato.onclick = fecharModalEditarContato;
    }

    // Fechar modal ao clicar fora dele
    if (modalEditarContato) {
        window.addEventListener('click', function(event) {
            if (event.target === modalEditarContato) {
                fecharModalEditarContato();
            }
        });
    }

    // Submeter formulário de editar contato
    const formEditarContato = document.getElementById('formEditarContato');
    if (formEditarContato) {
        formEditarContato.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const contatoId = document.getElementById('editarContatoId').value;
            const contatoDTO = {
                tipoContato: document.getElementById('editarContatoTipo').value,
                valorContato: document.getElementById('editarContatoValor').value,
                observacao: document.getElementById('editarContatoObservacao').value || null,
                clienteId: parseInt(document.getElementById('editarContatoClienteId').value)
            };

            fetch('/contatos/' + contatoId, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(contatoDTO)
            })
            .then(function(response) {
                if (response.ok) {
                    alert('Contato atualizado com sucesso!');
                    fecharModalEditarContato();
                    // Recarregar contatos no modal de visualização
                    const clienteId = parseInt(document.getElementById('editarContatoClienteId').value);
                    const clienteNome = document.getElementById('editarContatoClienteNome').textContent;
                    const cliente = { id: clienteId, nome: clienteNome };
                    abrirModalVerContatos(cliente);
                } else {
                    return response.json().then(function(data) {
                        let mensagem = 'Erro ao atualizar contato.';
                        if (data.message) {
                            mensagem = data.message;
                        }
                        alert(mensagem);
                    });
                }
            })
            .catch(function(error) {
                console.error('Erro: ', error);
                alert('Erro ao atualizar contato. Tente novamente.');
            });
        });
    }
});

