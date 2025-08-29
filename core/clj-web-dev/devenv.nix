{ pkgs, lib, config, inputs, ... }:

{
  # https://devenv.sh/basics/
  env.GREET = "devenv";

  # https://devenv.sh/packages/
  packages = with pkgs; [
    clojure-lsp
    clj-kondo
    cljfmt
    babashka
    (leiningen.override { jdk = pkgs.temurin-bin-21; })
    cacert
  ];

  env = {
    # LEIN_JAVA_CMD = "${pkgs.temurin-bin-21}/bin/java";
  };

  languages = {
    clojure.enable = true;
    java = {
      enable = true;
      jdk.package = pkgs.temurin-bin-21;
      maven.enable = true;
    };
  };

  # https://devenv.sh/languages/
  # languages.rust.enable = true;

  # https://devenv.sh/processes/
  # processes.cargo-watch.exec = "cargo-watch";

  # https://devenv.sh/services/
  # services.postgres.enable = true;

  services = {
    postgres = {
      enable = true;
      package = pkgs.postgresql;
      listen_addresses = "*";
      port = 5432;
      initialDatabases = [
        {
          name = "dev";
          user = "dev";
        }
      ];
      settings = {
        log_connections = true;
        log_statement = "all";
        logging_collector = true;
        log_disconnections = true;
        log_destination = lib.mkForce "syslog";
      };
      initialScript = ''
        CREATE USER dev WITH SUPERUSER;
      '';
    };

    redis = {
      enable = true;
      package = pkgs.redis;
      port = 6379;
    };
  };

  # https://devenv.sh/scripts/
  scripts.hello.exec = ''
    echo hello from $GREET
  '';

  enterShell = ''
    hello
    git --version
  '';

  # https://devenv.sh/tasks/
  # tasks = {
  #   "myproj:setup".exec = "mytool build";
  #   "devenv:enterShell".after = [ "myproj:setup" ];
  # };

  # https://devenv.sh/tests/
  enterTest = ''
    echo "Running tests"
    git --version | grep --color=auto "${pkgs.git.version}"
  '';

  # https://devenv.sh/git-hooks/
  # git-hooks.hooks.shellcheck.enable = true;

  # See full reference at https://devenv.sh/reference/options/
}
