import styled from "styled-components";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import TermOfCollectInfoBox from "./TermOfCollectInfoBox";
import TermOfServiceBox from "./TermOfServiceBox";

import "App.css";

const Main = styled.div`
  width: 40rem;
  max-width: 40rem;
  margin: 3rem auto;

  font-family: SCDream5;
  font-size: 1.6rem;
  overflow: auto;

  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;
const Content = styled.div``;

const Title = styled.div`
  font-size: 3.5rem;
  font-family: SCDream9;
  text-align: center;
  margin: 2rem 0;

  @media screen and (max-width: 768px) {
    font-size: 1.75rem;
    margin: 1rem 0;
  }
`;

const SignUpForm = styled.form.attrs({
  type: "submit",
})`
  @media screen and (max-width: 768px) {
    margin: 1.5rem auto;
  }
`;

const Role = styled.div`
  display: flex;
  justify-content: space-between;

  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;
const StudentBtn = styled.button`
  box-sizing: border-box;
  border: none;
  border-radius: 1.5rem;
  width: 19rem;
  max-width: 19rem;
  height: 5rem;

  font-family: SCDream5;
  cursor: pointer;
  font-size: 2rem;
  background-color: ${(prop) =>
    prop.role === "student" ? "#35a753" : "#f0f5f9"};

  @media screen and (max-width: 768px) {
    max-width: 19rem;
    width: 50%;
    font-size: 1.7rem;
  }
`;
const TeacherBtn = styled(StudentBtn)`
  background-color: ${(prop) =>
    prop.role === "teacher" ? "#35a753" : "#f0f5f9"};
`;

const InputList = styled.div`
  display: grid;
  grid-template-columns: 1fr;
  max-width: 40rem;
`;
const IdInput = styled.input.attrs({
  placeholder: "아이디",
})`
  box-sizing: border-box;
  padding-left: 1.5rem;
  width: 40rem;
  height: 4.5rem;
  margin: 1rem auto;
  font-size: 1.8rem;
  border: none;
  border-radius: 1.5rem;
  background-color: #f0f5f9;

  ::placeholder {
    font-size: 1.6rem;
    font-family: SCDream5;
  }
  :focus {
    outline: none;
    border: 0.2rem solid #35a753;
  }
`;
const PwInput = styled(IdInput).attrs(() => ({
  placeholder: "비밀번호",
  type: "password",
}))``;

const PhoneInput = styled(IdInput).attrs(() => ({
  placeholder: "전화번호 (ex : 010-1234-5678)",
}))``;

const EmailInput = styled(IdInput).attrs(() => ({
  placeholder: "이메일",
}))``;

// 이용약관 관련
const Term = styled.div`
  margin: 0.5rem auto;
`;
const ServiceInput = styled.input.attrs(() => ({
  type: "checkbox",
}))``;

const ServiceLabel = styled.label`
  &:hover {
    cursor: pointer;
  }
`;

const BtnList = styled.div`
  display: flex;
  justify-content: center;
  column-gap: 1.5rem;
  margin-top: 1.5rem;
`;

const GoBackBtn = styled.button`
  box-sizing: border-box;

  padding: 1rem 3rem;
  border: none;
  border-radius: 1.5rem;

  font-family: SCDream5;
  font-size: 1.5rem;
  background-color: #1e1d4d;
  text-decoration: none;
  color: white;
  &:hover {
    cursor: pointer;
  }
`;
const SignUpBtn = styled(GoBackBtn)``;

const SignUp = () => {
  const navigate = useNavigate();

  // 회원가입시 입력받는 변수들
  const [signUpInfo, setSignUpInfo] = useState({
    role: "student",
    id: "",
    pw: "",
    confirmpw: "",
    phone: "",
    email: "",
    // 이용약관 및 개인 정보 수집 동의 여부
    service: false,
    collectInfo: false,
    all: false,
  });

  const [alertMsg, setAlertMsg] = useState({
    id: "",
    pw: "",
    confirmpw: "",
    phone: "",
    email: "",
  });

  const [isValidation, setIsValidation] = useState({
    id: false,
    pw: false,
    confirmpw: false,
    phone: false,
    email: false,
    all: false,
  });

  const changeSignUpInfo = (e) => {
    const { value, name, type } = e.target;

    // 체크 박스라면 체크박스 on / off 여부
    if (type === "checkbox") {
      // 체크 박스중 전체 동의를 눌렀다면 전체 동의 클릭 여부에 따라 나머지도 체크 on / off 체크
      if (name === "all") {
        setSignUpInfo({
          ...signUpInfo,
          [name]: !signUpInfo[name],
          service: !signUpInfo[name],
          collectInfo: !signUpInfo[name],
        });
      } else {
        setSignUpInfo({ ...signUpInfo, [name]: !signUpInfo[name] });
      }
    } else {
      setSignUpInfo({ ...signUpInfo, [name]: value });
    }
  };

  // 학생, 선생님 유형선택하는 함수
  const changeRole = (e, role) => {
    e.preventDefault();
    setSignUpInfo({ ...signUpInfo, role: role });
  };

  // Intro 화면으로 돌아가는 함수
  // 추후에 trySignUp과 합치기
  const goBackPage = () => {
    navigate("/");
  };

  // 회원가입 시도
  const trySignUp = (e) => {
    e.preventDefault();

    if (
      isValidation.id &&
      isValidation.pw &&
      isValidation.confirmpw &&
      isValidation.phone &&
      isValidation.email &&
      isValidation.all
    ) {
      window.alert(
        "회원가입에 성공하셨습니다.\n가입하신 아이디로 로그인하세요."
      );
      document.location.href = "/";
    } else {
      window.alert("회원 정보를 다시한번 확인해주세요.");
    }
  };

  // 아이디 유효성 검사하는 로직
  const idValidation = () => {
    const regExp =
      /[ \{\}\[\]\/?.,;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=ㄱ-ㅎㅏ-ㅣ가-힣]/gi;

    if (regExp.test(signUpInfo.id)) {
      setAlertMsg({
        ...alertMsg,
        id: "특수문자와 한글은 사용할 수 없어요 :(",
      });
      setIsValidation({ ...isValidation, id: false });
    } else if (signUpInfo.id.length === 0) {
      setAlertMsg({ ...alertMsg, id: " " });
      setIsValidation({ ...isValidation, id: false });
    } else if (signUpInfo.id.length > 21) {
      setAlertMsg({ ...alertMsg, id: "id가 너무 길어요." });
      setIsValidation({ ...isValidation, id: false });
    } else if (signUpInfo.id.length < 5) {
      setAlertMsg({ ...alertMsg, id: "id가 너무 짧습니다." });
      setIsValidation({ ...isValidation, id: false });
    } else {
      setAlertMsg({ ...alertMsg, id: "적절한 id입니다." });
      setIsValidation({ ...isValidation, id: true });
    }
  };

  // 비밀번호 유효성 검사하는 로직
  const pwValidation = () => {
    const regExp = /^(?=.*[a-zA-Z])(?=.*[.,/?!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;

    // 사용자에게 보여줄 메세지
    let pwMsg;
    let confirmpwMsg;
    // 올바르게 작성했는지 확인 여부
    let pw = false;
    let confirmpw = false;
    if (signUpInfo.pw.length === 0) {
      pwMsg = " ";
    } else if (!regExp.test(signUpInfo.pw)) {
      pwMsg = "숫자, 영문, 특수문자를 포함한 8자이상으로 조합하세요.";
    } else {
      pwMsg = "안전한 비밀번호입니다.";
      pw = true;
    }

    if (signUpInfo.confirmpw.length === 0) {
      confirmpwMsg = " ";
    } else if (signUpInfo.pw === signUpInfo.confirmpw) {
      confirmpwMsg = "비밀번호가 일치합니다.";
      confirmpw = true;
    } else {
      confirmpwMsg = "비밀번호가 일치하지않습니다.";
    }
    setAlertMsg({ ...alertMsg, pw: pwMsg, confirmpw: confirmpwMsg });
    setIsValidation({ ...isValidation, pw: pw, confirmpw: confirmpw });
  };

  // 전화번호 유효성 검사
  const phoneValidation = () => {
    // const regExp =
    //   /[ \{\}\[\]\/?.,;:|\)*~`!^\_+┼<>@\#$%&\'\"\\\(\=ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]{10, 11}[-]{2}/gi;

    const regExp = /^(?:(010-\d{4})|(01[1|6|7|8|9]-\d{3,4}))-(\d{4})$/;

    if (signUpInfo.phone.length === 0) {
      setIsValidation({ ...isValidation, phone: false });
      setAlertMsg({ ...alertMsg, phone: " " });
    } else if (regExp.test(signUpInfo.phone)) {
      setIsValidation({ ...isValidation, phone: true });
      setAlertMsg({ ...alertMsg, phone: "적절한 전화번호입니다." });
    } else {
      setIsValidation({ ...isValidation, phone: false });
      setAlertMsg({
        ...alertMsg,
        phone: "전화번호를 다시한번 확인해주세요.",
      });
    }
  };

  // 이메일 유효성 검사
  const emailValidation = () => {
    const regExp =
      /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/gi;

    if (signUpInfo.email.length === 0) {
      setIsValidation({ ...isValidation, email: false });
      setAlertMsg({ ...alertMsg, email: " " });
    } else if (regExp.test(signUpInfo.email)) {
      setIsValidation({ ...isValidation, email: true });
      setAlertMsg({ ...alertMsg, email: "적절한 이메일입니다." });
    } else {
      setIsValidation({ ...isValidation, email: false });
      setAlertMsg({ ...alertMsg, email: "이메일을 다시한번 확인해주세요." });
    }
  };

  const termValidation = () => {
    if (signUpInfo.all === true) {
      setIsValidation({ ...isValidation, all: true });
    } else {
      setIsValidation({ ...isValidation, all: false });
    }
  };

  // 이용약관 동의 및 개인정보 수집 동의 여부에 따라 전체 동의 체크여부 변경
  useEffect(() => {
    if (signUpInfo.collectInfo === true && signUpInfo.service === true) {
      setSignUpInfo({ ...signUpInfo, all: true });
    } else {
      setSignUpInfo({ ...signUpInfo, all: false });
    }
  }, [signUpInfo.collectInfo, signUpInfo.all, signUpInfo.service]);

  // 아이디 유효성 검사
  useEffect(() => {
    idValidation();
  }, [signUpInfo.id]);

  // 비밀번호 유효성 검사 및 일치여부 검사
  useEffect(() => {
    pwValidation();
  }, [signUpInfo.confirmpw, signUpInfo.pw]);

  useEffect(() => {
    phoneValidation();
  }, [signUpInfo.phone]);

  useEffect(() => {
    emailValidation();
  }, [signUpInfo.email]);

  useEffect(() => {
    termValidation();
  }, [signUpInfo.all]);

  console.log(isValidation);

  return (
    <Main>
      <Content>
        <SignUpForm onSubmit={trySignUp}>
          <Title>Infomation</Title>
          <Role>
            <StudentBtn
              role={signUpInfo.role}
              onClick={(e) => changeRole(e, "student")}
            >
              학생
            </StudentBtn>
            <TeacherBtn
              role={signUpInfo.role}
              onClick={(e) => changeRole(e, "teacher")}
            >
              선생님
            </TeacherBtn>
          </Role>
          <InputList>
            <IdInput
              name="id"
              onChange={changeSignUpInfo}
              value={signUpInfo.id}
            />
            <div style={{ marginLeft: "0.5rem" }}>{alertMsg.id}</div>
            <PwInput
              name="pw"
              onChange={changeSignUpInfo}
              value={signUpInfo.pw}
            />
            <div style={{ marginLeft: "0.5rem" }}>{alertMsg.pw}</div>
            <PwInput
              name="confirmpw"
              onChange={changeSignUpInfo}
              value={signUpInfo.confirmpw}
            />
            <div style={{ marginLeft: "0.5rem" }}>{alertMsg.confirmpw}</div>
            <PhoneInput
              name="phone"
              onChange={changeSignUpInfo}
              value={signUpInfo.phone}
            />
            <div style={{ marginLeft: "0.5rem" }}>{alertMsg.phone}</div>
            <EmailInput
              name="email"
              onChange={changeSignUpInfo}
              value={signUpInfo.email}
            />
            <div style={{ marginLeft: "0.5rem" }}>{alertMsg.email}</div>
          </InputList>
          <Term>
            <ServiceLabel>
              <ServiceInput
                name="service"
                onChange={changeSignUpInfo}
                checked={signUpInfo.service}
              />
              &nbsp;&nbsp;[필수] 제이케이에듀 이용약관 동의
            </ServiceLabel>
            <TermOfServiceBox />
          </Term>
          <Term>
            <ServiceLabel>
              <ServiceInput
                name="collectInfo"
                onChange={changeSignUpInfo}
                checked={signUpInfo.collectInfo}
              />
              &nbsp;&nbsp;[필수] 개인정보 수집 및 이용 동의
            </ServiceLabel>
            <TermOfCollectInfoBox />
          </Term>
          <Term>
            <ServiceLabel>
              <ServiceInput
                name="all"
                onChange={changeSignUpInfo}
                checked={signUpInfo.all}
              />
              &nbsp;&nbsp; 모든 약관에 동의합니다.
            </ServiceLabel>
          </Term>
          <BtnList>
            <GoBackBtn onClick={goBackPage}>돌아가기</GoBackBtn>
            <SignUpBtn type="submit">가입하기</SignUpBtn>
          </BtnList>
        </SignUpForm>
      </Content>
    </Main>
  );
};

export default SignUp;
