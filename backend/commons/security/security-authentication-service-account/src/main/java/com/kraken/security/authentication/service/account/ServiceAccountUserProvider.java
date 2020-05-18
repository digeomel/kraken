package com.kraken.security.authentication.service.account;

import com.kraken.config.security.client.api.SecurityClientProperties;
import com.kraken.security.authentication.utils.AtomicUserProvider;
import com.kraken.security.client.api.SecurityClient;
import com.kraken.security.decoder.api.TokenDecoder;
import com.kraken.security.entity.token.KrakenToken;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ServiceAccountUserProvider extends AtomicUserProvider {

  SecurityClientProperties clientProperties;
  SecurityClient client;

  public ServiceAccountUserProvider(@NonNull final SecurityClientProperties clientProperties,
                                    final TokenDecoder decoder,
                                    @NonNull final SecurityClient client) {
    super(decoder, 60L);
    this.clientProperties = clientProperties;
    this.client = client;
  }

  @Override
  protected Mono<KrakenToken> newToken() {
    return client.clientLogin(clientProperties.getApi());
  }

  @Override
  protected Mono<KrakenToken> refreshToken(KrakenToken token) {
    return this.newToken();
  }

}
