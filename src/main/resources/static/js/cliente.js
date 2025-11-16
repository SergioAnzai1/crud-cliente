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
        if (!cpf.validity.valid) {
            cpf.classList.add('error');
            formularioValido = false;
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
                throw new Error('Erro ao salvar cliente: ' + response.status);
            }
            return response.json();
        })
        .then(function(data) {
            alert('Cliente cadastrado com sucesso!');
            window.location.href = '/';
        })
        .catch(function(error) {
            console.error('Erro:', error);
            alert('Erro ao cadastrar cliente. Tente novamente.');
        })
    });
});