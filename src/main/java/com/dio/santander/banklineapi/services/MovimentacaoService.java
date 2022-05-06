package com.dio.santander.banklineapi.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.santander.banklineapi.dto.MovimentacaoNovo;
import com.dio.santander.banklineapi.model.Correntista;
import com.dio.santander.banklineapi.model.Movimentacao;
import com.dio.santander.banklineapi.model.MovimentacaoTipo;
import com.dio.santander.banklineapi.repository.CorrentistaRepository;
import com.dio.santander.banklineapi.repository.MovimentacaoRepository;

@Service
public class MovimentacaoService {
	
	@Autowired
	private MovimentacaoRepository repository;
	
	@Autowired
	private CorrentistaRepository correntistaRepository;

	public void save(MovimentacaoNovo novo) {
		Movimentacao mov = new Movimentacao();
		mov.setContaId(novo.getContaId());
		mov.setDataHora(LocalDateTime.now());
		mov.setDescricao(novo.getDescricao());
		mov.setTipo(novo.getTipo());
		mov.setValor(novo.getValor() * (novo.getTipo()==MovimentacaoTipo.RECEITA ? 1 : -1));
		
		Correntista correntista = correntistaRepository.findById(mov.getContaId()).orElse(null);
		if (correntista!=null) {
			correntista.getConta().setSaldo(correntista.getConta().getSaldo()+mov.getValor());
			correntistaRepository.save(correntista);
		}
		
		repository.save(mov);
	}
}
