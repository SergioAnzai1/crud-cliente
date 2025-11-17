// Espera a pagina carregar
document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('formCliente');
    if(!form) {
        console.error('Formulario não encontrado');
        return;
    }
    const nome = document.getElementById('nome');
    const cpf = document.getElementById('cpf');
    const dataNascimento = document.getElementById('dataNascimento');
    const endereco = document.getElementById('endereco');

    const hoje = new Date().toISOString().split('T')[0];
    dataNascimento.setAttribute('max', hoje);

    function aplicarMascaraCPF(valor) {
        const apenasNumeros = valor.replace(/\D/g, '');
        const cpfLimitado = apenasNumeros.substring(0, 11);
        
        if (cpfLimitado.length <= 3) {
            return cpfLimitado;
        } else if (cpfLimitado.length <= 6) {
            return cpfLimitado.substring(0, 3) + '.' + cpfLimitado.substring(3);
        } else if (cpfLimitado.length <= 9) {
            return cpfLimitado.substring(0, 3) + '.' + cpfLimitado.substring(3, 6) + '.' + cpfLimitado.substring(6);
        } else {
            return cpfLimitado.substring(0, 3) + '.' + cpfLimitado.substring(3, 6) + '.' + cpfLimitado.substring(6, 9) + '-' + cpfLimitado.substring(9);
        }
    }

    function validarCPF(cpfComMascara) {
        const cpf = cpfComMascara.replace(/\D/g, '');
        return cpf.length === 11;
    }

    if (cpf) {
        cpf.addEventListener('input', function(e) {
            const novoValor = aplicarMascaraCPF(this.value);
            this.value = novoValor;
            
            if (this.classList.contains('error') && validarCPF(novoValor)) {
                this.classList.remove('error');
            }
        });

        cpf.addEventListener('blur', function() {
            const valor = this.value.trim();
            if (valor && !validarCPF(valor)) {
                this.classList.add('error');
                
                let mensagemErro = this.parentElement.querySelector('.error-message');
                if (!mensagemErro) {
                    mensagemErro = document.createElement('span');
                    mensagemErro.className = 'error-message';
                    this.parentElement.appendChild(mensagemErro);
                }
                mensagemErro.textContent = 'CPF deve ter 11 dígitos.';
            } else {
                this.classList.remove('error');
                const mensagemErro = this.parentElement.querySelector('.error-message');
                if (mensagemErro) {
                    mensagemErro.remove();
                }
            }
        });
    }

    const todosInputs = form.querySelectorAll('input');
    todosInputs.forEach(function(input){
        input.addEventListener('input', function () {
            if (this.classList.contains('error') && this.validity.valid) {
                this.classList.remove('error');
            }
        })
    })
    function validarDataAnterior(dataString) {
        if (!dataString) {
            return false;
        }
        const dataSelecionada = new Date(dataString);
        const hoje = new Date();

        hoje.setHours(0,0,0,0);
        dataSelecionada.setHours(0,0,0,0);

        return dataSelecionada < hoje;
    }
    form.addEventListener('submit', function(e) {
        e.preventDefault();

        todosInputs.forEach(function(input){
            input.classList.remove('error');
        })
        let formularioValido = true;
    
        if (!nome.validity.valid) {
            nome.classList.add('error');
            formularioValido = false;
        }
        // Valida CPF
        const cpfValue = cpf.value.trim();
        if (!cpfValue || !validarCPF(cpfValue)) {
            cpf.classList.add('error');
            formularioValido = false;
            // Mostra mensagem de erro se não existir
            let mensagemErro = cpf.parentElement.querySelector('.error-message');
            if (!mensagemErro) {
                mensagemErro = document.createElement('span');
                mensagemErro.className = 'error-message';
                cpf.parentElement.appendChild(mensagemErro);
            }
            mensagemErro.textContent = cpfValue ? 'CPF deve ter 11 dígitos.' : 'CPF é obrigatório.';
        }
        if (!dataNascimento.validity.valid || !validarDataAnterior(dataNascimento.value)) {
            dataNascimento.classList.add('error');
            formularioValido = false;
        }
        if (!formularioValido) {
            alert('Por favor, preencha todos os campos obrigatórios corretamente.');
            return;
        }
        const dadosCliente = {
            nome: nome.value.trim(),
            cpf: cpf.value.trim(),
            dataNascimento: dataNascimento.value,
            endereco: endereco.value.trim() || null
        };
        fetch('/clientes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dadosCliente)
        })
        .then(function(response) {
            if (!response.ok) {
                return response.text().then(function(mensagem) {
                    throw { status: response.status, message: mensagem };
                });
            }
            return response.json();
        })
        .then(function(data) {
            alert('Cliente cadastrado com sucesso!');
            window.location.href = '/';
        })
        .catch(function(error) {
            console.error('Erro:', error);
            if (error.status === 400 && error.message && error.message.includes('já cadastrado')) {
                cpf.classList.add('error');
                let mensagemErro = cpf.parentElement.querySelector('.error-message');
                if (!mensagemErro) {
                    mensagemErro = document.createElement('span');
                    mensagemErro.className = 'error-message';
                    cpf.parentElement.appendChild(mensagemErro);
                }
                mensagemErro.textContent = 'CPF já cadastrado.';
                alert('Este CPF já está cadastrado no sistema.');
            } else {
                const mensagem = error.message || 'Erro ao cadastrar cliente. Tente novamente.';
                alert(mensagem);
            }
        })
    });
});